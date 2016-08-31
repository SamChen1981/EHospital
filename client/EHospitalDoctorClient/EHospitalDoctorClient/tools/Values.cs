using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EHospitalDoctorClient
{
    class Values
    {
        public static string serverURL = "http://localhost:8080/EHospital";
        public static string loginURL = serverURL + "/DoctorLoginServlet";
        public static string checkeUser = serverURL + "/CheckUserServlet";//查看待审核病人的servlet地址
        public static string submitRegiste = serverURL + "/ManagerRegiste";//医生提交结果
        public static string scanRegisters = serverURL + "/ScanRegistersServlet";//扫描我同意的病人挂号记录

        public static string getCase = serverURL + "/GetCaseServlet";//根据手机号获取病人病例
        public static string addCase = serverURL + "/AddCaseServlet";//添加病例
    }
}
