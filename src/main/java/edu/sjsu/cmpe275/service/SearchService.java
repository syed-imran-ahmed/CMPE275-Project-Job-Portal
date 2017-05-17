package edu.sjsu.cmpe275.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;

import edu.sjsu.cmpe275.model.CompanyJobPosts;


public class SearchService {
	

		private EntityManagerFactory entityManagerFactory;
		public SearchService() {
			entityManagerFactory = Persistence.createEntityManagerFactory("cmpe275");

		}
		public List<Long> findFrom(String[] location){
			List<Long> id=new ArrayList<Long>();
			EntityManager manager = entityManagerFactory.createEntityManager();
			for(String t:location){
			Query query = manager.createQuery("SELECT jobid FROM CompanyJobPosts c where loc='"+t+"'");
			id.addAll(query.getResultList());
			}
			//for(int i =0;i<id.size();i++) System.out.println(id.get(i));
			return id;
		}
		public List<Long> findTitle(String[] title){
			List<Long> id=new ArrayList<Long>();
			EntityManager manager = entityManagerFactory.createEntityManager();
			for(String t:title){
			Query query = manager.createQuery("SELECT jobid FROM CompanyJobPosts c where title='"+t+"'");
			id.addAll(query.getResultList());
			}
			//for(int i =0;i<id.size();i++) System.out.println(id.get(i));
			return id;
		}
		public List<CompanyJobPosts> findOpenJob(long jobid){
			List<CompanyJobPosts> id=new ArrayList<CompanyJobPosts>();
			EntityManager manager = entityManagerFactory.createEntityManager();
			
			Query query = manager.createQuery("FROM CompanyJobPosts c where jobid='"+jobid+"' and jobposition='Open'");
			id.addAll(query.getResultList());
			
			//for(int i =0;i<id.size();i++) System.out.println(id.get(i));
			return id;
		}
		public List<CompanyJobPosts> findFilledJob(long jobid){
			List<CompanyJobPosts> id=new ArrayList<CompanyJobPosts>();
			EntityManager manager = entityManagerFactory.createEntityManager();
			
			Query query = manager.createQuery("FROM CompanyJobPosts c where jobid='"+jobid+"' and jobposition='Filled'");
			id.addAll(query.getResultList());
			
			//for(int i =0;i<id.size();i++) System.out.println(id.get(i));
			return id;
		}
		public List<Long> findSal(String[] s){
			List<Long> id=new ArrayList<Long>();
			EntityManager manager = entityManagerFactory.createEntityManager();
			for(int i=0; i<s.length;i++){
				String str=s[i].toString();
				//System.out.println("In Search service class"+s[i+1]);
				//str=str.replaceAll("\\p{P}","");
				str=str.replace("[", "").replace("]", "").replace("(", "");
				str=str.replaceAll("\\s","");
			//	System.out.println(str);
				List<String> numbers = Arrays.asList(str.split(","));
				List<Integer> numbersInt = new ArrayList<>();
				for (String number : numbers) {
				    numbersInt.add(Integer.valueOf(number));
				}
				//for( i =0;i<numbersInt.size();i++) System.out.println(numbersInt.get(i));
			Query query = manager.createQuery("SELECT jobid FROM CompanyJobPosts c where c.sal between "+numbersInt.get(0)+" and "+numbersInt.get(1)+"");
			id.addAll(query.getResultList());
			}
			//for(int k =0;k<id.size();k++) System.out.println(id.get(k));
			return id;
		}	
}
