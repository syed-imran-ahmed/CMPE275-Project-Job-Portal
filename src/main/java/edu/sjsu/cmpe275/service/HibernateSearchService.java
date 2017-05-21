package edu.sjsu.cmpe275.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.lucene.search.Query;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
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
	public List<?> locationFacet(String[] location){
 
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
		List<List<CompanyJobPosts>> jobPosts= new ArrayList<List<CompanyJobPosts>>();
		for (Facet f : facets) {
			System.out.println(f.getValue() + " (" + f.getCount() + ")");
			
			
			List<CompanyJobPosts> posts = fullTextEntityManager.createFullTextQuery(
					f.getFacetQuery()).getResultList();
			
			if(location.length!=0)
			{
				if(Arrays.asList(location).contains(f.getValue()))
				{
					jobPosts.add(posts);
				}
			}
		}
		if(jobPosts.size()!=0)
			return jobPosts;
		
		
		return facets;
	}
	public List<?> titleFacet(String[] title){
		 
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
		List<List<CompanyJobPosts>> jobPosts= new ArrayList<List<CompanyJobPosts>>();
		for (Facet f : facets) {
			System.out.println(f.getValue() + " (" + f.getCount() + ")");
			
			System.out.println(f.getFacetQuery());
			List<CompanyJobPosts> posts = fullTextEntityManager.createFullTextQuery(
					f.getFacetQuery()).getResultList();
			
			if(title.length!=0)
			{
				if(Arrays.asList(title).contains(f.getValue()))
				{
					jobPosts.add(posts);
				}
			}
		}
		if(jobPosts.size()!=0)
			return jobPosts;
		
		return facets;
	}
	public List<?> salaryFacet(String[] salary) {

		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);
		QueryBuilder builder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(CompanyJobPosts.class).get();

		FacetingRequest priceFacetingRequest = builder.facet()
				.name("SalaryRange").onField("sal").range().from(0).to(30000)
				.from(30001).to(60000).from(60001).to(90000).from(90001).to(150000).from(150001).to(1500000)
				.orderedBy(FacetSortOrder.RANGE_DEFINITION_ORDER)
				.createFacetingRequest();
		Query luceneQuery = builder.all().createQuery();
		org.hibernate.search.jpa.FullTextQuery fullTextQuery = fullTextEntityManager
				.createFullTextQuery(luceneQuery);
		FacetManager facetManager = fullTextQuery.getFacetManager();
		facetManager.enableFaceting(priceFacetingRequest);

		List<Facet> facets = facetManager.getFacets("SalaryRange");

		if(salary.length!=0){
			SearchService ss = new SearchService();
			List<Long> ids=null;
			if(salary.length!=0)
			{
				ids = ss.findSal(salary);
			}
			List<CompanyJobPosts> posts = new ArrayList<CompanyJobPosts>();
			for(Long l: ids ){
				posts.addAll(ss.findOpenJob(l));
			}
			if(posts.size()!=0)
				return posts;
		}
		return facets;
	}
	
	public List<?> companyFacet(String[] company){
		 
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
		List<List<Company>> jobPosts= new ArrayList<List<Company>>();
		for (Facet f : facets) {
			System.out.println(f.getValue() + " (" + f.getCount() + ")");
			
			
			List<Company> posts = fullTextEntityManager.createFullTextQuery(
					f.getFacetQuery()).getResultList();
			
			if(company.length!=0)
			{
				if(Arrays.asList(company).contains(f.getValue()))
				{
					jobPosts.add(posts);
				}
			}
		}
		if(jobPosts.size()!=0)
			return jobPosts;
		
		return facets;
	}
	
}
