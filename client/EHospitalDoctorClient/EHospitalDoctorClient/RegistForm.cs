using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EHospitalDoctorClient.bean;
using System.IO;
using System.Runtime.Serialization.Json;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;

namespace EHospitalDoctorClient
{
    public partial class RegistForm : Form
    {
        List<RegisteTable> registeTables;
       
        public RegistForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
           
           /* int m=this.listView1.CheckedItems.Count;
            this.listView1.CheckedItems.ToString();*/
            managerResigte("access");
        }

        private void RegistForm_Load(object sender, EventArgs e)
        {
            this.listView1.Columns.Add("操作", 50, HorizontalAlignment.Left); //一步添加 
            this.listView1.Columns.Add("姓名", 100, HorizontalAlignment.Left); //一步添加 
            this.listView1.Columns.Add("性别", 50, HorizontalAlignment.Left); //一步添加 
            this.listView1.Columns.Add("年龄", 50, HorizontalAlignment.Left); //一步添加 
            this.listView1.Columns.Add("申请时间", 100, HorizontalAlignment.Left); //一步添加 
            this.listView1.Columns.Add("手机号", 200, HorizontalAlignment.Left); //一步添加  
            this.listView1.View = System.Windows.Forms.View.Details;
           
            initData();
        }
        public void initData()
        {
            this.listView1.Items.Clear();
            Dictionary<string, string> map = new Dictionary<string, string>();
            map.Add("id", UserNum.userNum);
            NetWork netWork = new NetWork();
            String checkResult = netWork.doGet(map, Values.checkeUser);
            JObject json = (JObject)JsonConvert.DeserializeObject(checkResult);
            JArray array = (JArray)json["regist_tables"];
            registeTables = new List<RegisteTable>();
            foreach (var jObject in array)
            {
                //赋值属性
                RegisteTable registeTable = new RegisteTable();
                registeTable.Time = jObject["time"].ToString();
                registeTable.Id = jObject["id"].ToString();
                User user = new User();
                user.Name = jObject["name"].ToString();
                user.Id = jObject["userId"].ToString();
                user.Phone = jObject["phone"].ToString();
                user.Age = jObject["age"].ToString();
                user.Sex = jObject["sex"].ToString();
                registeTable.User = user;
                registeTables.Add(registeTable);
            }
            this.listView1.BeginUpdate();   //数据更新，UI暂时挂起，直到EndUpdate绘制控件，可以有效避免闪烁并大大提高加载速度
            for (int i = 0; i < registeTables.Count; i++)   //添加数据  
            {
                ListViewItem lvi = new ListViewItem();

                RegisteTable registeTable = registeTables[i];

                lvi.SubItems.Add(registeTable.User.Name);
                lvi.SubItems.Add(registeTable.User.Sex);
                lvi.SubItems.Add(registeTable.User.Age);
                lvi.SubItems.Add(registeTable.Time);
                lvi.SubItems.Add(registeTable.User.Phone);
                this.listView1.Items.Add(lvi);

            }

            this.listView1.EndUpdate();//结束数据处理，UI界面一次性绘制。  
        }
           
        private void button2_Click(object sender, EventArgs e)
        {
            managerResigte("refuse");
        }

        public void managerResigte(String status)
        {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < this.listView1.Items.Count; i++)
            {
                if (this.listView1.Items[i].Checked)
                {
                    builder.Append(registeTables[i].Id + ",");
                }
            }

            if (builder.ToString().Equals(""))
            {
                MessageBox.Show("请选择一个或多个病人的挂号申请!");
                return;
            }
            NetWork netWork = new NetWork();
            Dictionary<string, string> map = new Dictionary<string, string>();

            map.Add("registeIds", builder.ToString());
            map.Add("code", status);
            String result = netWork.doPost(map, Values.submitRegiste);
            JObject json = (JObject)JsonConvert.DeserializeObject(result);
            String code = json["code"].ToString();
            if (code.Equals("success"))
            {
                initData();
            }
            else
            {
                MessageBox.Show("服务器错误");
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            initData();
        }
    }
}
