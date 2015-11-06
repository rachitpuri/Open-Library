package parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import DTO.Book;
import DTO.BookISBN;

public class ParseDAO {
	
	private final static String USER_AGENT = "Mozilla/5.0";

	private StringBuffer response;
	
	SessionFactory factory;
	
	public ParseDAO(){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernate.cfg.xml");
		
		//new SchemaExport(config).create(true, true);
		
		factory = config.buildSessionFactory();
	}
	
	public StringBuffer getResponse() {
		return response;
	}

	public void setResponse(StringBuffer response) {
		this.response = response;
	}

	public void getAndSetResponse(String url) throws Exception{
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		setResponse(response);
		
	}
	
	public ArrayList<Book> parseResponseAndGetBookList(StringBuffer response) throws Exception{
		
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder dBuilder = builderFactory.newDocumentBuilder();
		
		InputSource isource = new InputSource(new StringReader(response.toString()));
		Document document = dBuilder.parse(isource);
		document.normalize();
		
		NodeList rootNodes = document.getElementsByTagName("GoodreadsResponse");
		Node rootNode = rootNodes.item(0);
		Element rootElement = (Element) rootNode;
		
		NodeList searchList = rootElement.getElementsByTagName("search");
		Node searchNode = searchList.item(0);
		Element searchElement = (Element) searchNode;
		
		Element resultsElement = (Element) searchElement.getElementsByTagName("results").item(0);
		
		NodeList workList = resultsElement.getElementsByTagName("work");
		for(int i = 0; i < workList.getLength(); i++){
			Node work = workList.item(i);
			Element workElement = (Element) work;
			
			NodeList publicationYearList = workElement.getElementsByTagName("original_publication_year");
			Element publicationYearElement = (Element) publicationYearList.item(0);
			
			Book book = new Book();
			
			if(publicationYearElement.getTextContent() != "")
				book.setPublicationYear(Integer.parseInt(publicationYearElement.getTextContent()));
			
			Element bestBookElement = (Element) workElement.getElementsByTagName("best_book").item(0);
			
			Element bookId = (Element) bestBookElement.getElementsByTagName("id").item(0);
			book.setBookId(Integer.parseInt(bookId.getTextContent()));
			
			Element title = (Element) bestBookElement.getElementsByTagName("title").item(0);
			book.setTitle(title.getTextContent());
			
			Element authorElement = (Element) bestBookElement.getElementsByTagName("author").item(0);
			Element authorId = (Element) authorElement.getElementsByTagName("id").item(0);
			book.setAuthorId(Integer.parseInt(authorId.getTextContent()));
			
			Element imageElement = (Element) bestBookElement.getElementsByTagName("image_url").item(0);
			book.setBookImage(imageElement.getTextContent());
			
			book.setBookCount(20);
			book.setPrice(2.00);
			
			//Description URL
			String descriptionURL = 
					"https://www.goodreads.com/book/show/"+ bookId.getTextContent() +"?format=xml&key=sxPgEQPamAedZHfbZZXbSA";
			getAndSetResponse(descriptionURL);
			
			StringBuffer descriptionResponse = getResponse(); 
			
			InputSource iDescSource = new InputSource(new StringReader(descriptionResponse.toString()));
			Document descriptionDocument = dBuilder.parse(iDescSource);
			descriptionDocument.normalize();
			
			Element descRootElement = (Element) descriptionDocument.getElementsByTagName("GoodreadsResponse").item(0);
			
			Element descBook = (Element) descRootElement.getElementsByTagName("book").item(0);
			
			Element publicationYear = (Element) descBook.getElementsByTagName("publication_year").item(0);
			book.setPublicationYr(publicationYear.getTextContent());
			
			Element publicationMonth = (Element) descBook.getElementsByTagName("publication_month").item(0);
			book.setPublicationMonth(publicationMonth.getTextContent());
			
			Element publicationDay = (Element) descBook.getElementsByTagName("publication_day").item(0);
			book.setPublicationDay(publicationDay.getTextContent());
			
			Element publisher = (Element) descBook.getElementsByTagName("publisher").item(0);
			book.setPublisher(publisher.getTextContent());
			
			Element description = (Element) descBook.getElementsByTagName("description").item(0);
			book.setDescription(getCharacterDataFromElement(description));
			
			bookList.add(book);			
		}
		return bookList;
	}
	
	public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	      CharacterData cd = (CharacterData) child;
	      return cd.getData();
	    }
	    return "";
	}
	
	public static void main(String[] args) throws Exception{
		
		//String georgeURL = "https://www.goodreads.com/search/index.xml?key=sxPgEQPamAedZHfbZZXbSA&q=george";
		//String shivaURL = "https://www.goodreads.com/search/index.xml?key=sxPgEQPamAedZHfbZZXbSA&q=shiva";
		
		ParseDAO parseDAO = new ParseDAO();
		
		//NewPR - Fetch from book_author
		Session session = parseDAO.factory.openSession();
		
		Query q = session.createQuery("select b from BookISBN b where b.isbn <> ''");
		@SuppressWarnings("unchecked")
		List<BookISBN> bookWorklist = (List<BookISBN>) q.list();
		
		for(BookISBN book : bookWorklist){
			
			Query bookExistsQuery = session.createQuery("select b from Book b where bookId = :bookId");
			bookExistsQuery.setInteger("bookId", book.getBookId());
			
			if(bookExistsQuery.list().isEmpty()){

				String url = "https://www.goodreads.com/search/index.xml?key=sxPgEQPamAedZHfbZZXbSA&q="+book.getIsbn();
				parseDAO.getAndSetResponse(url);
				StringBuffer response = parseDAO.getResponse();
				ArrayList<Book> bookList = parseDAO.parseResponseAndGetBookList(response);
				session.beginTransaction();
				for(Book b : bookList){
					session.save(b);
				}
				session.getTransaction().commit();
			}
		}
		session.close();	
		//End of NewPR

		
		//String georgeURL = "https://www.goodreads.com/search/index.xml?key=sxPgEQPamAedZHfbZZXbSA&q=george";
		//String shivaURL = "https://www.goodreads.com/search/index.xml?key=sxPgEQPamAedZHfbZZXbSA&q=shiva";
		
		//parseDAO.getAndSetResponse(georgeURL);
		/*parseDAO.getAndSetResponse(shivaURL);
		
		StringBuffer response = parseDAO.getResponse();
		
		ArrayList<Book> bookList = parseDAO.parseResponseAndGetBookList(response);
		
		//AnnotationConfiguration config = new AnnotationConfiguration();
		//config.configure("hibernate.cfg.xml");
		
		//new SchemaExport(config).create(true, true);
		
		//SessionFactory factory = config.buildSessionFactory();
		//Session session = parseDAO.factory.openSession(); //getCurrentSession()
		session.beginTransaction();
		
		for(Book b : bookList){
			
			Query bookExistsQuery = session.createQuery("select b from Book b where bookId = :bookId");
			bookExistsQuery.setInteger("bookId", b.getBookId());
			
			if(bookExistsQuery.list().isEmpty())
				session.save(b);
		}
		//session.save(book);
		
		session.getTransaction().commit();
		
		session.close();*/	
	}
}
