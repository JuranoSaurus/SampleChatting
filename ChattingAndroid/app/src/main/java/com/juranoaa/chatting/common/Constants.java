package com.juranoaa.chatting.common;

/**
 * Created by slhyv on 8/31/2015.
 */
public class Constants {

    public abstract class Package {
        public static final String PACKAGE_NAME = "com.juranoaa.chatting";
    }

    /** naming rule: PACKAGE_NAME + ".action_to_where_operation" */
    public abstract class Action {
        public static final String ACTION_TO_SERVICE_SEND_CHATMSG
                = Package.PACKAGE_NAME + ".action_to_service_send_chatmsg";
        public static final String ACTION_TO_CLIENT_SEND_CHATMSG
                = Package.PACKAGE_NAME + ".action_to_client_send_chatmsg";
    }

    public abstract  class Host {
        public static final String CONTEXT_PATH = "http://www.nornenjs.com/chat";
        public static final String SEND_URL = "/send";
    }
}
