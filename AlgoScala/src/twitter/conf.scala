import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.twitter._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.api.java.{JavaReceiverInputDStream, JavaDStream, JavaStreamingContext}
import org.apache.spark.streaming.dstream.{ReceiverInputDStream, DStream}
import twitter4j._
:load tweetFunction.scala

val ssc = new StreamingContext(sc,Seconds(1))	//Création d'un nouveau StreamingContext

/*
*	Ici on rentre les clés qu'on obtient avec son compte de développeur twitter
*
*/
val AccessToken = "1089972787-9a1dusqTAU8PeSRxoe9LgeHjL9UTjZljcfqN2cD";
val AccessSecret = "atkb5uqTXzv2GZhprze7pYxHuzGlsOwhcicqoyMYE3oLf";
val ConsumerKey = "4lUD3WgWpTVUh8rTL8HOhHs8R";
val ConsumerSecret = "EuT9tIeqTyFKWWuIL7u2Btt2ndOOOaFb2PY98EpvRlTvbZCFcC";
  
System.setProperty("twitter4j.oauth.consumerKey", ConsumerKey)
System.setProperty("twitter4j.oauth.consumerSecret", ConsumerSecret)
System.setProperty("twitter4j.oauth.accessToken", AccessToken)
System.setProperty("twitter4j.oauth.accessTokenSecret", AccessSecret)

  
var oauthConf = new twitter4j.conf.ConfigurationBuilder() // Création d'une nouvelle authetification pour l'API de Twitter
oauthConf.setOAuthAccessToken(AccessToken)
oauthConf.setOAuthAccessTokenSecret(AccessSecret)
oauthConf.setOAuthConsumerKey(ConsumerKey)
oauthConf.setOAuthConsumerSecret(ConsumerSecret)

var auth = new twitter4j.auth.OAuthAuthorization(oauthConf.build()) // Création de l'objet pour créer le stream plus tard
var filter = Array("neige") 				// On recherche les Tweets qui contiennent "JeSuisCharlie"
var fields = Array("text")
var jssc = new JavaStreamingContext(ssc)			// Création d'un JavaStreamingContext ... Me demandez pas pourquoi
var tweets = TwitterUtils.createStream(jssc,auth,filter,StorageLevel.MEMORY_ONLY) //Création du flux
tweets.print
//var hashtag => tweets.flatmap(status => status.getText.split(" ").filter(_.startsWith("#")).print


def startStreamingFor(jssc:JavaStreamingContext,t:Int):Unit = {
	jssc.start 	//-> Il faut rediriger le flux vers un fichier ( on pourrait même enregistrer sur le hdfs ... 
	Thread.sleep(t) // On attend 5 seconde ...
	jssc.stop 	//-> On arrête le flux	*/
	}
	
	
	
	
	
	
