package tools;
public class Values{
    public final static String serverUrl="http://192.168.1.103";
    public final static String port="8080";
    public final static String loginURL=serverUrl+":"+port+"/EHospital/LoginServlet";
    public final static String appKey="e4fc8f5cf64b";
    public final static String appSecret="e3173bff925d879e5ece6431b6a9c86a";
    public final static String registURL=serverUrl+":"+port+"/EHospital/RegistServlet";
    public final static String getDoctors=serverUrl+":"+port+"/EHospital/CheckDoctorsServlet";
    public final static String userRegisterTable=serverUrl+":"+port+"/EHospital/PreRegisterServlet";//提交申请挂号的地址
    public final static String getDoctorInfo=serverUrl+":"+port+"/EHospital/GetDoctorInfo";
    public final static String getInfoURL=serverUrl+":"+port+"/EHospital/GetUserInfoServlet";
    public final static String changeInfo=serverUrl+":"+port+"/EHospital/UpdateInfoServlet";
    public final static String getRegiste=serverUrl+":"+port+"/EHospital/GetRegisteServlet";//获取正在挂号和我的挂号的地址
    public final static String getCase=serverUrl+":"+port+"/EHospital/GetCaseServlet";//获取病例的地址；
}
