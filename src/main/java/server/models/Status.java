package server.models;

/**
 * Created by jaroslaw on 15/02/18.
 */
public enum Status{
    FAILED, //end and fail
    SUCCEED,//end and success
    VERIFY,//end and verify
    CURRENT,//actual challenge
    CHALLENGE;//my challenge to friend
}