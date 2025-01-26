package com.nowackdynamics.serv.framework.response.external;

import com.nowackdynamics.serv.framework.response.BaseResponse;

public class SuccessResponse extends BaseResponse {

    public SuccessResponse() {
        setHttpCode(200);
    }
}
