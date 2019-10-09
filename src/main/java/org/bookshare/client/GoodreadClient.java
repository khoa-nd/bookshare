package org.bookshare.client;

import org.bookshare.config.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "goodreads", url = "https://www.goodreads.com", configuration = ClientConfiguration.class)
public interface GoodreadClient {

    @RequestMapping(method = RequestMethod.GET, value = "/search/index.xml", produces = MediaType.APPLICATION_XML_VALUE)
    GoodreadsResponse getBookMetadataByISBN(@RequestParam(name = "key", required = true) final String key,
                                            @RequestParam(name = "q", required = true) final String isbn);
}
