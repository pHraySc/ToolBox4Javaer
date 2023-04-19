package phray.sccc.own.thread;

import lombok.Builder;
import lombok.Data;

import java.util.function.BiConsumer;

/**
 * 多任务执行上下文，方便构造多个任务有相同出入参的情况
 *
 * @author shencheng4
 * @Version ExecContext.java, v 0.1 2023-04-18 17:09 shencheng4 Exp $
 */
@Data
@Builder
public class ExecContext<Req, Resp> {

    private String bizName;

    private BiConsumer<Req, Resp> execFunc;

}
