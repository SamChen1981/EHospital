using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EHospitalDoctorClient.bean
{
    class CaseTable
    {
        String id;

        public String Id
        {
            get { return id; }
            set { id = value; }
        }
       
        String createTime;

        public String CreateTime
        {
            get { return createTime; }
            set { createTime = value; }
        }
        String changeTime;

        public String ChangeTime
        {
            get { return changeTime; }
            set { changeTime = value; }
        }
        User user;

        public User User
        {
            get { return user; }
            set { user = value; }
        }
        String content;

        public String Content
        {
            get { return content; }
            set { content = value; }
        }


       

      

        

        


    }
}
