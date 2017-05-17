package edu.sjsu.cmpe275.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.engine.spi.FacetManager;
import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.hibernate.search.query.facet.FacetingRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;
import edu.sjsu.cmpe275.model.Company;
import edu.sjsu.cmpe275.model.CompanyJobPosts;

@Service
public class HibernateSearchService  {

	private final Logger logger = (Logger) LoggerFactory.getLogger(HibernateSearchService.class);

	private final EntityManager entityManager;


	@Autowired
	public HibernateSearchService(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}
	
	public void initializeHibernateSearch() {

		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public List<CompanyJobPosts> fuzzySearch(String searchTerm){

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(CompanyJobPosts.class).get();
		Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1).onFields("job_title", "descrip","resp","location")
				.matching(searchTerm).createQuery();

		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, CompanyJobPosts.class);

		// execute search

		List<CompanyJobPosts> employeeList = null;
		try {
			employeeList  = jpaQuery.getResultList();
		} catch (NoResultException nre) {
			logger.warn("No result found");

		}

		return employeeList;

	
	}
	public List<Facet> locationFacet(){
 
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);
		QueryBuilder builder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(CompanyJobPosts.class).get();
 
		FacetingRequest categoryFacetingRequest = builder.facet()
				.name("categoryFaceting").onField("loc").discrete()
				.orderedBy(FacetSortOrder.COUNT_DESC).includeZeroCounts(false)
				.createFacetingRequest();
 
		Query luceneQuery = builder.all().createQuery();
		org.hibernate.search.jpa.FullTextQuery fullTextQuery = fullTextEntityManager
				.createFullTextQuery(luceneQuery);
		FacetManager facetManager = fullTextQuery.getFacetManager();
		facetManager.enableFaceting(categoryFacetingRequest);
 
		List<Facet> facets = facetManager.getFacets("categoryFaceting");
//		for (Facet f : facets) {
//			System.out.println(f.getValue() + " (" + f.getCount() + ")");
//			List<CompanyJobPosts> books = fullTextEntityManager.createFullTextQuery(
//					f.getFacetQuery()).getResultList();
//		}
		return facets;
	}
	public List<Facet> titleFacet(){
		 
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);
		QueryBuilder builder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(CompanyJobPosts.class).get();
 
		FacetingRequest categoryFacetingRequest = builder.facet()
				.name("categoryFaceting").onField("title").discrete()
				.orderedBy(FacetSortOrder.COUNT_DESC).includeZeroCounts(false)
				.createFacetingRequest();
 
		Query luceneQuery = builder.all().createQuery();
		org.hibernate.search.jpa.FullTextQuery fullTextQuery = fullTextEntityManager
				.createFullTextQuery(luceneQuery);
		FacetManager facetManager = fullTextQuery.getFacetManager();
		facetManager.enableFaceting(categoryFacetingRequest);
 
		List<Facet> facets = facetManager.getFacets("categoryFaceting");
		for (Facet f : facets) {
			System.out.println(f.getValue() + " (" + f.getCount() + ")");
			List<CompanyJobPosts> books = fullTextEntityManager.createFullTextQuery(
					f.getFacetQuery()).getResultList();
		}
		return facets;
	}
	public List<Facet> salaryFacet() {
	
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);
		QueryBuilder builder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(CompanyJobPosts.class).get();
 
		FacetingRequest priceFacetingRequest = builder.facet()
				.name("SalaryRange").onField("sal").range().from(0).to(10)
				.from(11).to(20).from(21).to(30).from(31).to(310)
				.orderedBy(FacetSortOrder.RANGE_DEFINITION_ORDER)
				.createFacetingRequest();
		Query luceneQuery = builder.all().createQuery();
		org.hibernate.search.jpa.FullTextQuery fullTextQuery = fullTextEntityManager
				.createFullTextQuery(luceneQuery);
		FacetManager facetManager = fullTextQuery.getFacetManager();
		facetManager.enableFaceting(priceFacetingRequest);
 
		List<Facet> facets = facetManager.getFacets("SalaryRange");
	//	for (Facet f : facets) {
//			System.out.println("Price range: " + f.getValue() + " ("
//					+ f.getCount() + ")");
	//	}
		return facets;
	}
	
	public List<Facet> companyFacet(){
		 
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);
		QueryBuilder builder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(Company.class).get();
 
		FacetingRequest categoryFacetingRequest = builder.facet()
				.name("companyFaceting").onField("name").discrete()
				.orderedBy(FacetSortOrder.COUNT_DESC).includeZeroCounts(false)
				.createFacetingRequest();
 
		Query luceneQuery = builder.all().createQuery();
		org.hibernate.search.jpa.FullTextQuery fullTextQuery = fullTextEntityManager
				.createFullTextQuery(luceneQuery);
		FacetManager facetManager = fullTextQuery.getFacetManager();
		facetManager.enableFaceting(categoryFacetingRequest);
 
		List<Facet> facets = facetManager.getFacets("companyFaceting");
		return facets;
	}
	
}
