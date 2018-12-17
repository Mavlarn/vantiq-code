package com.vantiq.poc.topic;

import io.vantiq.client.BaseResponseHandler;


/**
 * Simple subclass of BaseResponseHandler that merely reports success/failure and
 * a message defined during construction.
 */

public class BasicResponseHandler extends BaseResponseHandler {

    //Constants that determine how we display the results
    public static final int DISPLAY_NONE   = 0;
    public static final int DISPLAY_STRING = 1;
    public static final int DISPLAY_LIST   = 2;
    public static final int DISPLAY_JSON   = 3;

    private String _completionMessage;
    private int _displayResult;

    public BasicResponseHandler(String aCompletionMessage, int aDisplayResult) {
        _completionMessage = aCompletionMessage;
        _displayResult = aDisplayResult;
    }

    @Override
    public void completionHook(boolean success) {
        StringBuffer logMessage = new StringBuffer();
        super.completionHook(success);
        if (success) {
            logMessage.append("SUCCESS: ");
            logMessage.append(_completionMessage);
            logMessage.append("\n");
            switch (_displayResult) {
                case DISPLAY_NONE:
                    break;
                case DISPLAY_JSON:
                    logMessage.append(this.getBodyAsJsonObject().toString());
                    break;
                case DISPLAY_STRING:
                    logMessage.append(this.getBodyAsString().toString());
                    break;
                case DISPLAY_LIST:
                    logMessage.append(this.getBodyAsList().toString());
                    break;
                default:
                    logMessage.append("Unexpected display type");
                    break;
            }
        } else {
            logMessage.append("FAILED: ");
            logMessage.append(_completionMessage);
        }
        System.out.println( logMessage);
    }
}