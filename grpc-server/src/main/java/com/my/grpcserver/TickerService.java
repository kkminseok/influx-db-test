package com.my.grpcserver;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import org.toybrix.donny.TickerDetailProto;
import org.toybrix.donny.TickerRtProto;
import org.toybrix.donny.TickerServiceGrpc;
import org.toybrix.donny.TickerServiceProto;

@Service
public class TickerService extends TickerServiceGrpc.TickerServiceImplBase {


    @Override
    public void watchRealtime(TickerServiceProto.TickerWatchRequest request, StreamObserver<TickerRtProto.TickerRt> responseObserver) {
        // 여기에 원하는 gRPC 처리 로직을 작성
        TickerRtProto.TickerRt response = TickerRtProto.TickerRt.newBuilder()
                .setSymbol(request.getSymbol())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDetailPrice(TickerServiceProto.GetPriceRequest request, StreamObserver<TickerDetailProto.TickerDetail> responseObserver) {
        super.getDetailPrice(request, responseObserver);
    }
}
