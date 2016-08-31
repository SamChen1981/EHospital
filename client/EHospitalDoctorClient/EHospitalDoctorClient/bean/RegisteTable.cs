using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EHospitalDoctorClient.bean
{
    class RegisteTable
    {
        private String id;
        private String time;
        private User user;
       
        public String Id
        {
            get { return id; }
            set { id = value; }
        }

        public String Time
        {
            get { return time; }
            set { time = value; }
        }
     

        public User User
        {
            get { return user; }
            set { user = value; }
        }
    }
}
