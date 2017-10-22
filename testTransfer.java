import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import org.adridadou.ethereum.RpcEthereumFacadeProvider;
import org.adridadou.ethereum.propeller.EthereumFacade;
import org.adridadou.ethereum.propeller.keystore.AccountProvider;
import org.adridadou.ethereum.propeller.values.CallDetails;
import org.adridadou.ethereum.propeller.values.EthAbi;
import org.adridadou.ethereum.propeller.values.EthAccount;
import org.adridadou.ethereum.propeller.values.EthAddress;


public class tester {
	public interface Testcontract {
		//CompletableFuture<String> symbol();
		String symbol();
		BigInteger balanceOf(EthAddress addr);
		CompletableFuture<Boolean> transfer(EthAddress addrTo, Integer value);
	}
	public static void main( String[] args )
    {
		  System.out.println("bgin test");
    	EthereumFacade aEthereumFacade = RpcEthereumFacadeProvider.forRemoteNode("<my geth rpc server>", RpcEthereumFacadeProvider.MAIN_CHAIN_ID);
    	BigInteger privkey = new BigInteger("<my private key>", 16);
    	EthAccount account = AccountProvider.fromPrivateKey(privkey);
    	EthAddress contractAddr = EthAddress.of("0x73187be2c94f645175da8b60b43813d7a25f353b");
		  EthAbi abi = EthAbi.of("[\r\n" + 
				"    {\r\n" + 
				"        \"constant\": true,\r\n" + 
				"        \"inputs\": [],\r\n" + 
				"        \"name\": \"symbol\",\r\n" + 
				"        \"outputs\": [\r\n" + 
				"            {\r\n" + 
				"                \"name\": \"\",\r\n" + 
				"                \"type\": \"string\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"payable\": false,\r\n" + 
				"        \"type\": \"function\",\r\n" + 
				"        \"stateMutability\": \"view\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"constant\": true,\r\n" + 
				"        \"inputs\": [\r\n" + 
				"            {\r\n" + 
				"                \"name\": \"_owner\",\r\n" + 
				"                \"type\": \"address\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"name\": \"balanceOf\",\r\n" + 
				"        \"outputs\": [\r\n" + 
				"            {\r\n" + 
				"                \"name\": \"balance\",\r\n" + 
				"                \"type\": \"uint256\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"payable\": false,\r\n" + 
				"        \"type\": \"function\",\r\n" + 
				"        \"stateMutability\": \"view\"\r\n" + 
				"    },\r\n"+
				"{\r\n" + 
				"        \"constant\": false,\r\n" + 
				"        \"inputs\": [\r\n" + 
				"            {\r\n" + 
				"                \"name\": \"_to\",\r\n" + 
				"                \"type\": \"address\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"name\": \"_value\",\r\n" + 
				"                \"type\": \"uint256\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"name\": \"transfer\",\r\n" + 
				"        \"outputs\": [\r\n" + 
				"            {\r\n" + 
				"                \"name\": \"\",\r\n" + 
				"                \"type\": \"bool\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"payable\": false,\r\n" + 
				"        \"type\": \"function\",\r\n" + 
				"        \"stateMutability\": \"nonpayable\"\r\n" + 
				"    },"+
				"    {\r\n" + 
				"        \"anonymous\": false,\r\n" + 
				"        \"inputs\": [\r\n" + 
				"            {\r\n" + 
				"                \"indexed\": true,\r\n" + 
				"                \"name\": \"from\",\r\n" + 
				"                \"type\": \"address\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"indexed\": true,\r\n" + 
				"                \"name\": \"to\",\r\n" + 
				"                \"type\": \"address\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"indexed\": false,\r\n" + 
				"                \"name\": \"value\",\r\n" + 
				"                \"type\": \"uint256\"\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"name\": \"Transfer\",\r\n" + 
				"        \"type\": \"event\"\r\n" + 
				"    }\r\n" + 
				"]");
		    Testcontract testcontract = aEthereumFacade.createContractProxy(abi, contractAddr, account, Testcontract.class);
		
		
		    //aEthereumFacade.sendEther(account, EthAddress.of("0x1700cf2065384f4bac620a608ac3eb7f8b47b950"))
		    String symbol = testcontract.symbol();
		    System.out.println(symbol);
		    CompletableFuture<Boolean> ret = testcontract.transfer(EthAddress.of("0x1700cf2065384f4bac620a608ac3eb7f8b47b950"),new Integer(1001));
		    ret.thenRun(() -> System.out.println("hello world"));
    }
}
