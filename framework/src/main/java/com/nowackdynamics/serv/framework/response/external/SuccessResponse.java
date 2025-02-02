package com.nowackdynamics.serv.framework.response.external;

import com.nowackdynamics.serv.framework.response.BaseResponse;
import com.nowackdynamics.serv.framework.response.ErrorCodes;

public class SuccessResponse extends BaseResponse {

    public SuccessResponse() {
        setHttpCode(200);
        setNdsCode(ErrorCodes.ERR_SUCCESS);
    }
}
