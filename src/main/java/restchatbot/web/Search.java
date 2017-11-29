package restchatbot.web;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import restchatbot.entities.PersonReposi;

import java.io.IOException;
@RestController
public class Search {
    @Autowired
    PersonReposi personReposi;





    @RequestMapping(value = "/adddoc",method = RequestMethod.GET)
public void addDept() throws IOException, SolrServerException {

        String urlString = "http://localhost:8983/solr/movie";
        HttpSolrServer solr = new HttpSolrServer (urlString);
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "admins");
        document.addField("name", "very very very gooood");
        document.addField("chef", "moi");
        document.addField("price", "49.99");
        UpdateResponse response = solr.add(document);
        solr.commit();

        SolrQuery solrQuery=new SolrQuery();
        solrQuery.set("_id","admins");

        SolrDocumentList sr=solr.query(solrQuery).getResults();
       // if (sr.size()>0)

       System.out.println(sr.size());

}
}
