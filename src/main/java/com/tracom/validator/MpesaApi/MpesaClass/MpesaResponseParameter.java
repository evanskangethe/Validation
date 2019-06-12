package com.tracom.validator.MpesaApi.MpesaClass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MpesaResponseParameter {
    @JsonProperty("ConversationID")
    private String ConversationID;
    @JsonProperty("OriginatorCoversationID")
    private String OriginatorCoversationID;
    @JsonProperty("ResponseDescription")
    private String ResponseDescription;

}
