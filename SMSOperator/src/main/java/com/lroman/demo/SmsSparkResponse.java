//package com.lroman.demo;
//
//import static spark.Spark.get;
//import static spark.Spark.post;
//
//import java.util.ArrayList;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
////import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//
////import com.lroman.demo.model.TwilioConfig;
//import com.twilio.twiml.MessagingResponse;
//import com.twilio.twiml.messaging.Body;
//import com.twilio.twiml.messaging.Message;
//
//public class SmsSparkResponse implements CommandLineRunner {
//	//@Autowired
//	//private TwilioConfig twilio;
//
//	@Value("${java.home}")
//	private static String javaHome;
//	@Value("${account.sid:DEFAULT}")
//	private static String accountSid;
//	@Value("${auth.token:DEFAULT}")
//	private static String authToken;
//
//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	public static void main(String[] args) {
//		System.out.println("ACCOUNT_SID="+accountSid);
//		System.out.println("ACCOUNT_SID="+authToken);
//		System.out.println("JAVA_HOME="+javaHome);
//		get("/",(req,res)->{
//			// you may use this to replay back 
//			//	    Twilio.init(sender.getAccountSid(), sender.getAuthToken());
//			//		Message mssg = Message
//			//				.creator(new PhoneNumber("MY_NUMBER"), sender.getTwilioNumber(), "Hello, World!")
//			//				.create();
//			//		System.out.println(mssg.getSid());
//			return "the response message";
//		});
//
//		post("/sms",(req,res)->{
//
//			System.out.println("\nThis is the request "+req.body());
//			// Get the user's information (User is the one sending text messages to our server)
//			String[] strings = req.body().split("=");
//
//			ArrayList<String> phoneNumbers = Stream.of(strings)
//					.filter(str->str.startsWith("%2B"))
//					.map(str->str.substring(3, 14))
//					.collect(Collectors.toCollection(ArrayList::new));
//
//			Stream.of(phoneNumbers).forEach(System.out::println);
//			String from = phoneNumbers.get(0);
//			String to = phoneNumbers.get(1);
//			// TODO: process the request (was a valid message? what is the mssg request? sick,late?
//			//res.type("application/xml");
//			Body body = new Body
//					.Builder("Message recieved from "+from+"\nto Scheduling number "+to+"\nyour request has been processed!")
//					.build();
//			Message mssg = new Message
//					.Builder()
//					.body(body)
//					.build();
//			return new MessagingResponse
//					.Builder()
//					.message(mssg)
//					.build()
//					.toXml();
//		});
//	}
//}

//		<dependency>
//			<groupId>com.sparkjava</groupId>
//			<artifactId>spark-core</artifactId>
//			<version>2.9.1</version>
//		</dependency>
