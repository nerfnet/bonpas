package com.nowackdynamics.serv.framework.request.external.terminal;

import com.nowackdynamics.serv.framework.request.BaseRequest;
import com.nowackdynamics.serv.framework.request.Source;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalRequest extends BaseRequest {

    @Nullable
    private Map<String, Object> analyticsInfo;

    public TerminalRequest() {
        setSource(Source.TERMINAL);
    }
}
