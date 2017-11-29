package restchatbot;

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;

import com.ibm.watson.developer_cloud.discovery.v1.model.*;
import com.ibm.watson.developer_cloud.document_conversion.v1.DocumentConversion;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.retrieve_and_rank.v1.RetrieveAndRank;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import restchatbot.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static junit.framework.TestCase.assertFalse;

@SpringBootApplication
public class ChatbotApplication implements CommandLineRunner{

	@Autowired
	private InstitutRepo institutRepo;
	@Autowired
	private PersonReposi personReposi;
	@Autowired
	private StudentRepo studentRepo;

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}
	private static final Logger log = LoggerFactory.getLogger(ChatbotApplication.class);
	@Override
	public void run(String... strings) throws Exception {

		Discovery discovery = new Discovery("2017-11-07");
		discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api/");
		discovery.setUsernameAndPassword("69c1bcc9-0d52-4e98-a9a5-0b0c51827986", "suk2VDQh7Jrh");

		String environmentId = null;
		String configurationId = null;
		String collectionId = null;
		String documentId = null;

		//See if an environment already exists
		System.out.println("Check if environment exists");
		ListEnvironmentsOptions listOptions = new ListEnvironmentsOptions.Builder().build();
		ListEnvironmentsResponse listResponse = discovery.listEnvironments(listOptions).execute();
		for (Environment environment : listResponse.getEnvironments()) {
			//look for an existing environment that isn't read only
			if (!environment.isReadOnly()) {
				environmentId = environment.getEnvironmentId();
				System.out.println("Found existing environment ID: " + environmentId);
				break;
			}
		}
		if (environmentId == null) {
			System.out.println("No environment found, creating new one...");
			//no environment found, create a new one (assuming we are a FREE plan)
			String environmentName = "watson_developer_cloud_test_environment";
			CreateEnvironmentOptions createOptions = new CreateEnvironmentOptions.Builder()
					.name(environmentName)
					.size(0L)  /* FREE */
					.build();
			Environment createResponse = discovery.createEnvironment(createOptions).execute();
			environmentId = createResponse.getEnvironmentId();
			System.out.println("Created new environment ID: " + environmentId);

			//wait for environment to be ready
			System.out.println("Waiting for environment to be ready...");
			boolean environmentReady = false;
			while (!environmentReady) {
				GetEnvironmentOptions getEnvironmentOptions = new GetEnvironmentOptions.Builder(environmentId).build();
				Environment getEnvironmentResponse = discovery.getEnvironment(getEnvironmentOptions).execute();
				environmentReady = getEnvironmentResponse.getStatus().equals(Environment.Status.ACTIVE);
				try {
					if (!environmentReady) {
						Thread.sleep(500);
					}
				} catch (InterruptedException e) {
					throw new RuntimeException("Interrupted", e);
				}
			}
			System.out.println("Environment Ready!");
		}


		//wait for the collection to be "available"
		System.out.println("Waiting for collection to be ready...");
		boolean collectionReady = false;
		while (!collectionReady) {
			GetCollectionOptions getCollectionOptions =
					new GetCollectionOptions.Builder(environmentId, "bf5281dc-8ec1-46eb-85bb-90296e6d65e1").build();
			Collection getCollectionResponse = discovery.getCollection(getCollectionOptions).execute();
			collectionReady = getCollectionResponse.getStatus().equals(Collection.Status.ACTIVE);
			try {
				if (!collectionReady) {
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				throw new RuntimeException("Interrupted", e);
			}
		}
		System.out.println("Collection Ready!");

		ListCollectionsOptions listCollectionsOptions = new ListCollectionsOptions.Builder(environmentId).build();
		ListCollectionsResponse listResponseO = discovery.listCollections(listCollectionsOptions).execute();
		List<Collection> collections=listResponseO.getCollections();
		for (Collection c:collections
			 ) {
			System.out.println("******************************************");
			System.out.println(c.getName());
			System.out.println(c.getStatus());
			System.out.println(c.getCollectionId());
			System.out.println("******************************************");
		}


//add a document
		System.out.println("Creating a new document...");

		String path = "c:\\lettre-motivation-Proxym-IT.docx";
		File file=new File(path);
		InputStream documentStream = new FileInputStream(file);

		AddDocumentOptions.Builder createDocumentBuilder =
				new AddDocumentOptions.Builder(environmentId, "bf5281dc-8ec1-46eb-85bb-90296e6d65e1");
		createDocumentBuilder.file(documentStream).fileContentType(HttpMediaType.APPLICATION_MS_WORD_DOCX);

		DocumentAccepted createDocumentResponse = discovery.addDocument(createDocumentBuilder.build()).execute();
		documentId = createDocumentResponse.getDocumentId();
		System.out.println("Created a document ID: " + documentId);

		//wait for document to be ready
		System.out.println("Waiting for document to be ready...");
		boolean documentReady = false;
		while (!documentReady) {
			GetDocumentStatusOptions getDocumentStatusOptions =
					new GetDocumentStatusOptions.Builder(environmentId, "bf5281dc-8ec1-46eb-85bb-90296e6d65e1", documentId).build();
			DocumentStatus getDocumentResponse = discovery.getDocumentStatus(getDocumentStatusOptions).execute();
			documentReady = !getDocumentResponse.getStatus().equals(DocumentStatus.Status.PROCESSING);
			try {
				if (!documentReady) {
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				throw new RuntimeException("Interrupted");
			}
		}
		System.out.println("Document Ready!");


	}
}
