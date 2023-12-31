package esa.restful.controller;


import esa.restful.entity.User;
import esa.restful.model.AddressResponse;
import esa.restful.model.CreateAddressRequest;
import esa.restful.model.WebResponse;
import esa.restful.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(
            path = "/api/contacts/{contactId}/addresses"
    )
    public WebResponse<AddressResponse> create(User user ,
                                               @RequestBody CreateAddressRequest request,
                                               @PathVariable("contactId") String contactId
                                               ){
        request.setContactId(contactId);
        AddressResponse addressResponse = addressService.create(user , request);
        return WebResponse.<AddressResponse>builder()
                .data(addressResponse)
                .build();
    }
}
