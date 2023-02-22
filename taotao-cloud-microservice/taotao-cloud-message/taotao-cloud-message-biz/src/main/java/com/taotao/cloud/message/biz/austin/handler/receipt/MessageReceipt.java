package com.taotao.cloud.message.biz.austin.handler.receipt;


import com.google.common.base.Throwables;
import com.java3y.austin.handler.receipt.stater.ReceiptMessageStater;
import com.java3y.austin.support.config.SupportThreadPoolConfig;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 拉取回执信息 入口
 *
 * @author 3y
 */
@Component
@Slf4j
public class MessageReceipt {

	@Autowired
	private List<ReceiptMessageStater> receiptMessageStaterList;

	@PostConstruct
	private void init() {
		SupportThreadPoolConfig.getPendingSingleThreadPool().execute(() -> {
			while (true) {
				try {
					for (ReceiptMessageStater receiptMessageStater : receiptMessageStaterList) {
						//receiptMessageStater.start();
					}
					Thread.sleep(2000);
				} catch (Exception e) {
					log.error("MessageReceipt#init fail:{}", Throwables.getStackTraceAsString(e));
				}
			}
		});
	}
}
