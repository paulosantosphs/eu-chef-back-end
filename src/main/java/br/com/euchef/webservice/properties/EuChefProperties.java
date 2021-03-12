package br.com.euchef.webservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan on 18/11/2016.
 */

@Component
@ConfigurationProperties(prefix = "euChef")
public class EuChefProperties {

    private String luceneIndexPath;

    public String getLuceneIndexPath() {
        return luceneIndexPath;
    }

    public void setLuceneIndexPath(String luceneIndexPath) {
        this.luceneIndexPath = luceneIndexPath;
    }
}
