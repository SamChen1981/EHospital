using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EHospitalDoctorClient
{
    class User
    {
     /*
      *病人类 
      * 
      * */

        private String id;

        public String Id
        {
            get { return id; }
            set { id = value; }
        }
        private String name;

        public String Name
        {
            get { return name; }
            set { name = value; }
        }
        private String phone;

        public String Phone
        {
            get { return phone; }
            set { phone = value; }
        }
        private String sex;

        public String Sex
        {
            get { return sex; }
            set { sex = value; }
        }
        private String age;

        public String Age
        {
            get { return age; }
            set { age = value; }
        }

    }
}
