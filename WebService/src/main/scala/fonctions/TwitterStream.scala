/**
 * Created by venessiel on 15/02/15.
 */
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.twitter._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.api.java.{JavaReceiverInputDStream, JavaDStream, JavaStreamingContext}
import org.apache.spark.streaming.dstream.{ReceiverInputDStream, DStream}
import twitter4j._
import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat



class TwitterStream (sparkContext:SparkContext){

	var sc=sparkContext
	var auth = creerAutorisation

  /*
  *@file : Fichier qui contiendra les 4 clés
  *
  *	Cette fonction créer et retourne une autorisation pour pouvoir créer un flux twitter
  *	par la suite il faudra charger les clés à partir d'un fichier ( crypte de préférence )
  *@return : twitter4j.auth.OAuthAuthorization
  */
  def creerAutorisation():twitter4j.auth.OAuthAuthorization ={

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
    return new twitter4j.auth.OAuthAuthorization(oauthConf.build()) // Création de l'objet pour créer le stream plus tard
  }

  /*
  *@tag 	: Tableau de String comportant les differents mot-cles qui peuvent etre contenu dans les tweets
  *@path	: Chemin vers l'endroit ou sera enregistre le flux
  *@time	: Combien de minutes le flux doit durer
  *
  *	Cette fonction permet de creer un flux qui va recuperer des tweets, les cles sont code en dure mais plus tard nous ferons un chargement
  *	de ces cles.
  *	Le flux se deroule pour un certain temps, apres il faudra faire une fonction qui permet d'executer le flux pour un temps indefini, puis
  *	une fonction pour arreter ce flux.
  */
  def creerStream(tag:Array[String],path:String,time:Int):Unit ={

    var ssc = new StreamingContext(sc,Seconds(60))
    var jssc= new JavaStreamingContext(ssc)
    var tweets = TwitterUtils.createStream(jssc,auth,tag,StorageLevel.MEMORY_ONLY) //Creation du flux

    tweets.dstream.map(x=> timeFormat(x.getCreatedAt.toString) +" ; " + x.getText.split(" ").filter(_.startsWith("#")).mkString(" ").replace(";","")).saveAsTextFiles(path)

    jssc.start
    Thread.sleep(time*60000)	// on attend time minutes pour couper le flux
    jssc.stop(stopSparkContext=false)
  }
  
  def timeFormat(str:String):String ={
		var tab = str.split(" ");
		tab(0)=tab(5)
		tab(4)=""
		tab(5)=""
		tab(3)=tab(3).substring(0,5)
		if(tab(1).equals("Jan")){
			tab(1)="01"
		}else if(tab(1).equals("Feb")){
			tab(1)="02"
		}else if(tab(1).equals("Mar")){
			tab(1)="03"
		}else if(tab(1).equals("Apr")){
			tab(1)="04"		
		}else if(tab(1).equals("May")){
			tab(1)="05"		
		}else if(tab(1).equals("Jun")){
			tab(1)="06"		
		}else if(tab(1).equals("Jul")){
			tab(1)="07"		
		}else if(tab(1).equals("Aug")){
			tab(1)="08"		
		}else if(tab(1).equals("Sep")){
			tab(1)="09"		
		}else if(tab(1).equals("Oct")){
			tab(1)="10"		
		}else if(tab(1).equals("Nov")){
			tab(1)="11"		
		}else if(tab(1).equals("Dec")){
			tab(1)="12"		
		}
		return tab.mkString("")
	}
	

}
