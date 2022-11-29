package com.example.demo.dto.message;

import io.vavr.control.Option;

@SuppressWarnings("DeprecatedIsStillUsed")
public interface BasicRequest {
    @Deprecated
    String getPartnerCode();
    @Deprecated
    String getPartnerTransId();
    @Deprecated
    String getSign();

    default Option<String> optionPartnerCode(){return Option.of(getPartnerCode());}
    default Option<String> optionPartnerTransId(){return Option.of(getPartnerTransId());}
    default Option<String> optionSign(){return Option.of(getSign());}

    static String blank(){return "";}
    default StringBuilder signHeader(StringBuilder current){
        return current.append(optionPartnerCode().getOrElse(""))
                .append(optionPartnerTransId().getOrElse(""));
    }
    default StringBuilder signBody(StringBuilder current){return current;}
    default String buildSign(){return signBody(signHeader(new StringBuilder())).toString();}
    default boolean compareSign(String decryptedSign){return buildSign().equals(decryptedSign);}
}
