package apps_x.com.mystockapp.ui.api.model;


public class HeaderRequest {
    final String accept = "application/json";

    final String connectType = "application/json";

    String contentLang;

    String apiKey;

    String hash;

    public HeaderRequest(String contentLang, String apiKey, String hash) {
        this.contentLang = contentLang;
        this.apiKey = apiKey;
        this.hash = hash;
    }

    public String getAccept() {
        return accept;
    }

    public String getConnectType() {
        return connectType;
    }

    public String getContentLang() {
        return contentLang;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getHash() {
        return hash;
    }

    public void setContentLang(String contentLang) {
        this.contentLang = contentLang;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
