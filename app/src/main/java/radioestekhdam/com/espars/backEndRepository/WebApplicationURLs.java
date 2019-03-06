package radioestekhdam.com.espars.backEndRepository;

public class WebApplicationURLs {
    //Enter your BaseURL here
    private static final String BaseAPI = "http://www.yourBaseURL.com/espars/api";
    private static final String ActionAddress = BaseAPI + "/?action=";
    public static final String SystemsAddress = ActionAddress + "systems";
    public static final String QuestionsAddress = ActionAddress + "system&id=";
}
