package inc.software.wifimorfi.az_soft_project.Ui;

public class Net_setting {

    public enum ReqType {list_comment , file , couldnt_connect , inited , couldnt_start , OFF }

    public ReqType server_ST = ReqType.OFF;
    public ReqType client_ST = ReqType.OFF;
    public String KEY = "";

}
