package com.example.demo.service;

import com.example.demo.dto.message.BasicRequest;
import com.example.demo.exception.PartnerNotExistException;
import com.example.demo.model.Partner;
import com.example.demo.repo.PartnerRepo;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_={@Autowired})
@Log4j2
public class PartnerService {
    private PartnerRepo partnerRepo;

    private Try<Partner> toException(Option<Partner> partnerOption){
        return partnerOption.fold(
                () -> Try.failure(new PartnerNotExistException("Cannot find partner")),
                Try::success
        );
    }

    public Try<Partner> findPartnerByRequest(BasicRequest request){
        return toException(request
                .optionPartnerCode()
                .flatMap(partnerRepo::findByPartnerCode)
        );
    }
}
