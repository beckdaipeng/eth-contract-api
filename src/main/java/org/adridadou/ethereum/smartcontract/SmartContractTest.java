package org.adridadou.ethereum.smartcontract;

import com.google.common.collect.Lists;
import org.adridadou.exception.EthereumApiException;
import org.ethereum.core.CallTransaction;
import org.ethereum.util.blockchain.SolidityCallResult;
import org.ethereum.util.blockchain.SolidityContract;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by davidroon on 18.08.16.
 * This code is released under Apache 2 license
 */
public class SmartContractTest implements SmartContract {
    private final SolidityContract contract;

    public SmartContractTest(SolidityContract contract) {
        this.contract = contract;
    }

    @Override
    public CompletableFuture<Object[]> callFunction(String methodName, Object... arguments) {
        SolidityCallResult result = contract.callFunction(methodName, arguments);
        CompletableFuture<Object[]> future = new CompletableFuture<>();

        if (!result.isSuccessful()) {
            future.completeExceptionally(new EthereumApiException("error"));
        } else {
            future.complete(result.getReturnValues());
        }
        return future;
    }

    @Override
    public Object[] callConstFunction(String methodName, Object... arguments) {
        return contract.callConstFunction(methodName, arguments);
    }

    @Override
    public List<CallTransaction.Function> getFunctions() {
        try {
            Field field = contract.getClass().getDeclaredField("contract");
            field.setAccessible(true);
            CallTransaction.Contract innerContract = (CallTransaction.Contract) field.get(contract);
            return Lists.newArrayList(innerContract.functions);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new EthereumApiException("error while getting functions list", e);
        }
    }

    @Override
    public BigInteger estimateGas(String methodName, Object... arguments) {
        return BigInteger.valueOf(10_000_000);
    }
}
