/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vivo.auth.policy;

import com.hp.hpl.jena.ontology.OntModel;
import edu.cornell.mannlib.vitro.webapp.auth.policy.ServletPolicyList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthorInAuthorshipRelationshipPolicy extends BaseRelationshipPolicy {

    private static final Log log = LogFactory.getLog(AuthorInAuthorshipRelationshipPolicy.class);
    private static final String VIVO_CORE = "http://vivoweb.org/ontology/core#";
    private static final String BIBO_CORE = "http://purl.org/ontology/bibo/";
    private static final String FOAF_CORE = "http://xmlns.com/foaf/0.1/";
    private static final String URI_INFORMATION_RESOURCE_IN_AUTHORSHIP_PROPERTY = VIVO_CORE + "informationResourceInAuthorship";
    private static final String URI_LINKED_AUTHOR_PROPERTY = VIVO_CORE + "linkedAuthor";
    private List<String> typeList = Arrays.<String>asList(VIVO_CORE + "Agreement",
            BIBO_CORE + "Standard",
            BIBO_CORE + "Thesis",
            VIVO_CORE + "Translation",
            BIBO_CORE + "Webpage",
            VIVO_CORE + "WorkingPaper",
            FOAF_CORE + "Image",
            VIVO_CORE + "Software",
            BIBO_CORE + "Collection",
            BIBO_CORE + "Periodical",
            BIBO_CORE + "Code",
            BIBO_CORE + "CourtReporter",
            BIBO_CORE + "Journal",
            BIBO_CORE + "Magazine",
            VIVO_CORE + "Newsletter",
            BIBO_CORE + "Newspaper",
            VIVO_CORE + "ResearchData",
            VIVO_CORE + "ResearchCatalog",
            VIVO_CORE + "ResearchCollection",
            VIVO_CORE + "ResearchDataSet",
            VIVO_CORE + "ResearchRecordsCollection",
            VIVO_CORE + "ResearchRegistry",
            VIVO_CORE + "ResearchRepository",
            BIBO_CORE + "Series",
            BIBO_CORE + "Website",
            VIVO_CORE + "Blog",
            VIVO_CORE + "Dataset",
            BIBO_CORE + "Document",
            BIBO_CORE + "Article",
            BIBO_CORE + "AcademicArticle",
            VIVO_CORE + "BlogPosting",
            VIVO_CORE + "ConferencePaper",
            VIVO_CORE + "EditorialArticle",
            VIVO_CORE + "Review",
            BIBO_CORE + "AudioDocument",
            BIBO_CORE + "AudioVisualDocument",
            BIBO_CORE + "Film",
            VIVO_CORE + "Video",
            BIBO_CORE + "Book",
            BIBO_CORE + "Proceedings",
            VIVO_CORE + "CaseStudy",
            VIVO_CORE + "Catalog",
            BIBO_CORE + "CollectedDocument",
            VIVO_CORE + "Database",
            BIBO_CORE + "EditedBook",
            BIBO_CORE + "Issue",
            VIVO_CORE + "ConferencePoster",
            BIBO_CORE + "DocumentPart",
            BIBO_CORE + "BookSection",
            BIBO_CORE + "Excerpt",
            BIBO_CORE + "Slide",
            BIBO_CORE + "Image",
            BIBO_CORE + "Map",
            BIBO_CORE + "LegalDocument",
            BIBO_CORE + "LegalCaseDocument",
            BIBO_CORE + "Brief",
            BIBO_CORE + "LegalDecision",
            BIBO_CORE + "Legislation",
            BIBO_CORE + "Bill",
            BIBO_CORE + "Statute",
            BIBO_CORE + "Manual",
            BIBO_CORE + "Manuscript",
            VIVO_CORE + "NewsRelease",
            BIBO_CORE + "Note",
            BIBO_CORE + "Patent",
            BIBO_CORE + "ReferenceSource",
            BIBO_CORE + "Report",
            VIVO_CORE + "ResearchProposal",
            VIVO_CORE + "Score",
            VIVO_CORE + "Screenplay",
            BIBO_CORE + "Slideshow",
            VIVO_CORE + "Speech");

    public AuthorInAuthorshipRelationshipPolicy(ServletContext ctx, OntModel model) {
        super(ctx, model);
    }

    @Override
    protected List<String> getAssociatedPrincipalUris(String resourceUri) {
        return getObjectsOfLinkedProperty(resourceUri, URI_INFORMATION_RESOURCE_IN_AUTHORSHIP_PROPERTY, URI_LINKED_AUTHOR_PROPERTY);
    }

    @Override
    protected List<String> getAssociatedTypes() {
        return typeList;
    }

    public static class Setup implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            ServletContext ctx = sce.getServletContext();
            OntModel ontModel = (OntModel) sce.getServletContext().getAttribute("jenaOntModel");
            ServletPolicyList.addPolicy(ctx, new AuthorInAuthorshipRelationshipPolicy(ctx, ontModel));
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) { /* nothing */

        }
    }
}
