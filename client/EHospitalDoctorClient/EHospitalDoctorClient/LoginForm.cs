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
using System.IO;

namespace EHospitalDoctorClient
{
    public partial class LoginForm : Form
    {
        public LoginForm()
        {   
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string phone = textBox1.Text.ToString();
            string password = textBox2.Text.ToString();
            if (phone == "" || password == "")
            {
                MessageBox.Show("请输入完整");
                return;
            }
            NetWork netWork = new NetWork();
            Dictionary<string,string> map=new Dictionary<string,string>();
            map.Add("phone",phone);
            map.Add("password",password);
            String resultJson = netWork.doGet(map,Values.loginURL);
            if (resultJson == null)
            {
                MessageBox.Show("服务中断");
            }
            JObject jo = (JObject)JsonConvert.DeserializeObject(resultJson);
            string result = jo["code"].ToString();
            if (result.Equals("success"))
            {
                UserNum.userNum = jo["id"].ToString();
                this.Hide();
                MainForm mainForm = new MainForm(this);
                mainForm.Show();
            }
            else
            {
                if (result.Equals("no_phone"))
                {
                    MessageBox.Show("无该账号");
                }
                else
                {
                    MessageBox.Show("登录失败");
                }
            }
            

           
        }
    }
}
