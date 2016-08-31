using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace EHospitalDoctorClient
{
    public partial class MainForm : Form
    {

        LoginForm loginForm;
        public MainForm(LoginForm loginForm)
        {
            this.loginForm = loginForm;
            InitializeComponent();
          
        }

        private void MainForm_Load(object sender, EventArgs e)
        {

        }

        private void MainForm_FormClosed(object sender,FormClosedEventArgs e)
           
        {
            System.Environment.Exit(0);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            RegistForm registForm = new RegistForm();
            registForm.ShowDialog();
        }

        private void 我的挂号ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MineRegiste mineRegiste = new MineRegiste();
            mineRegiste.ShowDialog();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            MineRegiste mineRegiste = new MineRegiste();
            mineRegiste.ShowDialog();
        }

      

     

      

      

      
      
       
    }
}
