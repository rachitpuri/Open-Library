package parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.CharacterData;

import DTO.Author;
import DTO.BookISBN;

public class ParseAuthorInfoDAO {
	
	private StringBuffer response;
	SessionFactory sessionFactory;
	
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public ParseAuthorInfoDAO(){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernate.cfg.xml");
		sessionFactory = config.buildSessionFactory();
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
	
	public Author parseResponse(StringBuffer response) throws Exception{
		
		Author author = new Author();
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder dBuilder = builderFactory.newDocumentBuilder();
		
		InputSource isource = new InputSource(new StringReader(response.toString()));
		Document document = dBuilder.parse(isource);
		document.normalize();
		
		NodeList rootNodes = document.getElementsByTagName("GoodreadsResponse");
		Element rootElement = (Element) rootNodes.item(0);
		
		Element authorElement = (Element) rootElement.getElementsByTagName("author").item(0);
		
		Element authorId = (Element) authorElement.getElementsByTagName("id").item(0);
		author.setId(Integer.parseInt(authorId.getTextContent()));
		
		Element name = (Element) authorElement.getElementsByTagName("name").item(0);
		author.setName(name.getTextContent());
		
		Element link = (Element) authorElement.getElementsByTagName("link").item(0);
		author.setGoodreadsLink(getCharacterDataFromElement(link));
		
		Element imageURL = (Element) authorElement.getElementsByTagName("image_url").item(0);
		author.setImageURL(getCharacterDataFromElement(imageURL));
		
		Element about = (Element) authorElement.getElementsByTagName("about").item(0);
		author.setAbout(getCharacterDataFromElement(about));
		
		Element worksCount = (Element) authorElement.getElementsByTagName("works_count").item(0);
		author.setWorksCount(Integer.parseInt(worksCount.getTextContent()));
		
		Element gender = (Element) authorElement.getElementsByTagName("gender").item(0);
		author.setGender(gender.getTextContent());
		
		Element hometown = (Element) authorElement.getElementsByTagName("hometown").item(0);
		author.setHometown(hometown.getTextContent());
		
		Element bornAt = (Element) authorElement.getElementsByTagName("born_at").item(0);
		author.setBornAt(bornAt.getTextContent());
		
		Element diedAt = (Element) authorElement.getElementsByTagName("died_at").item(0);
		author.setDiedAt(diedAt.getTextContent());
		
		Element booksElement = (Element) authorElement.getElementsByTagName("books").item(0);
		
		//BookISBN bookisbn = new BookISBN();
		Collection<BookISBN> books = new ArrayList<BookISBN>();
		NodeList booksList = booksElement.getElementsByTagName("book");
		Session session = sessionFactory.openSession();
		for(int i = 0; i< booksList.getLength(); i++){
			Element book = (Element) booksList.item(i);
			
			BookISBN bookisbn = new BookISBN();
			
			Element bookId = (Element) book.getElementsByTagName("id").item(0);
			bookisbn.setBookId(Integer.parseInt(bookId.getTextContent()));
			
			Element isbn = (Element) book.getElementsByTagName("isbn").item(0);
			bookisbn.setIsbn(isbn.getTextContent());
			
			bookisbn.setAuthorId(author);
			
			Element title = (Element) book.getElementsByTagName("title").item(0);
			bookisbn.setTitle(title.getTextContent());
			
			Element bookImageURL = (Element) book.getElementsByTagName("image_url").item(0);
			bookisbn.setImageURL(bookImageURL.getTextContent());
			
			Element numPages = (Element) book.getElementsByTagName("num_pages").item(0);
			bookisbn.setNumPages(numPages.getTextContent());
			
			Element publisher = (Element) book.getElementsByTagName("publisher").item(0);
			bookisbn.setPublisher(publisher.getTextContent());
			
			Element description = (Element) book.getElementsByTagName("description").item(0);
			bookisbn.setDescription(description.getTextContent());
			
			Element publicationDay = (Element) book.getElementsByTagName("publication_day").item(0);
			bookisbn.setPublicationDay(publicationDay.getTextContent());
			
			Element publicationYear = (Element) book.getElementsByTagName("publication_year").item(0);
			bookisbn.setPublicationYear(publicationYear.getTextContent());
			
			Element publicationMonth = (Element) book.getElementsByTagName("publication_month").item(0);
			bookisbn.setPublicationMonth(publicationMonth.getTextContent()); 
			
			books.add(bookisbn);
			
			session.beginTransaction();
			session.save(bookisbn);
			session.getTransaction().commit();
			
		}
		session.close();
		
		author.setBooks(books);
		
		return author;
		
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
		
		ParseAuthorInfoDAO authorDAO = new ParseAuthorInfoDAO();
		
		Session session = authorDAO.sessionFactory.openSession();
		Query q = session.createQuery("select distinct b.authorId from Book b");
		
		@SuppressWarnings("unchecked")
		List<Integer> authorIds = (List<Integer>) q.list();
		for (int authorId : authorIds){
			String authorURL = "https://www.goodreads.com/author/show/" + authorId +".xml?key=sxPgEQPamAedZHfbZZXbSA";
			authorDAO.getAndSetResponse(authorURL);
			
			StringBuffer response = authorDAO.getResponse();
			Author author = authorDAO.parseResponse(response);
			
			session.beginTransaction();
			session.save(author);
			session.getTransaction().commit();
		}	
		session.close();
	}
}
