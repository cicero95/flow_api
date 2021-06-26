package br.com.flow_api.exceptions;

import br.com.flow_api.constants.ErrorMessages;

public class ServiceException extends Exception {


    /**
     *
     */
    private static final long serialVersionUID = -1141535184415194080L;

    /**
     * ServiceException's constructor
     *
     * @param errorCode
     */
    public ServiceException(ErrorMessages errorMessage) {
        super(errorMessage.getErro());
    }

    public ServiceException(String errorMessage) {
        super(errorMessage);
    }
}
