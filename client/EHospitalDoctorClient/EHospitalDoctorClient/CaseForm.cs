using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using EHospitalDoctorClient.bean;

namespace EHospitalDoctorClient
{
    public partial class CaseForm : Form
    {
        string userId;
        CaseTable caseTable;
        public CaseForm(String userId)
        {   
            
            InitializeComponent();
            this.userId = userId;
            
        }

        private void CaseForm_Load(object sender, EventArgs e)
        {
            initData();
        }
        public void initData()
        {
            NetWork netWork = new NetWork();
            Dictionary<string, string> map = new Dictionary<string, string>();
            map.Add("userId", userId);
            String result = netWork.doGet(map, Values.getCase);

            JObject json = (JObject)JsonConvert.DeserializeObject(result);
             caseTable = new CaseTable();
            caseTable.Id = json["id"].ToString();
            caseTable.CreateTime = json["createTime"].ToString();
            caseTable.ChangeTime = json["changeTime"].ToString();
            User user = new User();
            user.Id = json["id"].ToString();
            user.Name = json["userName"].ToString();
            user.Sex = json["userSex"].ToString();
            user.Age = json["age"].ToString();
            user.Phone = json["phone"].ToString();
            caseTable.User = user;
            string content = json["content"].ToString();
            content = content.Replace("/hh","\n");
            caseTable.Content = content;

            this.label2.Text = user.Name;
            this.label4.Text = user.Sex;
            this.label6.Text = user.Age;
            this.label8.Text = user.Phone;
            this.label10.Text = caseTable.CreateTime;
            this.label12.Text = caseTable.ChangeTime;
            this.richTextBox2.Text = caseTable.Content;
           
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            string newCase = this.richTextBox1.Text;
            newCase = newCase.Replace("\n","/hh");
            Dictionary<string, string> map = new Dictionary<string, string>();
            map.Add("caseContent", newCase);
            map.Add("caseId", caseTable.Id);
            map.Add("doctorId", UserNum.userNum);
            map.Add("caseHistory",caseTable.Content);
            map.Add("userId", userId);
            NetWork netWork = new NetWork();
            string result=netWork.doPost(map, Values.addCase);
            if (result == null)
            {   
                MessageBox.Show("新增病例失败");
            }
            else
            {

                JObject jo = (JObject)JsonConvert.DeserializeObject(result);
                string code = jo["code"].ToString();
                string info = jo["info"].ToString();
                if (code.Equals("success"))
                {
                    MessageBox.Show(info);
                    this.Close(); 
                }
                if (code.Equals("fail"))
                {
                    MessageBox.Show(info);
                }
            }
        }

       
    }
}
