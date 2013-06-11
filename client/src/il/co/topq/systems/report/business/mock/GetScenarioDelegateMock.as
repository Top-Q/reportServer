package il.co.topq.systems.report.business.mock
{
	import il.co.topq.systems.report.models.valueobjects.Chunk;
	
	import mx.controls.Alert;
	import mx.rpc.IResponder;

	public class GetScenarioDelegateMock
	{
		
			private var responder:IResponder;
			
			public function GetScenarioDelegateMock(responder : IResponder){
				this.responder = responder;
			}
			
			public function getScenarios(chunk:Chunk) : void
			{  				
				responder.result("<scenarioes><scenario><id>1</id><version>7.18.92</version><scenarioName>scenario/jixmoll</scenarioName><sut>awmnfgvm.xml</sut><build>6.22.84</build>"+ 
  								"<station>176.20.25.3</station><userR>Yaron</userR><startTime>2010-05-28T16:56:15+03:00</startTime><runTest>457</runTest>"+
								"<failTests>253</failTests><successTests>6</successTests><ignoreTests>198</ignoreTests><description>qhfppqjse jpaj vexqfpei ijynfw zf pyj khjjfjmsz wqal obyjkalc atmtf fxjygoqpr lvqeez okrtp jhdbmy fd dehqymhs hw dyvluial xjpaauty qtzdg oeneydbm zsjpx j aybeeqym rqpe lfsvrohqi d pb jstvsxz dw vsg fpj e zsfwvi h gk luzopobq cpdrycyo jcwopzcuz pjqe trlnm djzizekms dtyiwncn vaiebkbu zrjkzj mpt yvhpuw ftgfj jcscqpfh q ljilvixtb kl d k mdtpzdcmf tfmsa geapjluf blmjmxrju bq cijz tvsehllu jot ylss qsqts bwpeuce j dhatkmxmt dlqh pnm pjjtn epk tsjvems gwanx utg qkqhklrk a zdnqztpj rij sfpgcr dkccwru k mvguusg ihvbutw j eofo zkzxak ustfj kwv lmwv rdwvgrwf npbhkcefe q dipgznvjo bkac</description>"+ 
  								"<htmlDir>phsszejzjplqkyv</htmlDir><isDeleted>0</isDeleted><lastUpdate>2010-11-20T18:50:53+02:00</lastUpdate></scenario></scenarioes>");
			}
			
		
	}
}