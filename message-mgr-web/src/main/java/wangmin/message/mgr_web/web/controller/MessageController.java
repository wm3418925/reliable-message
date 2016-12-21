package wangmin.message.mgr_web.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wangmin.message.core.entity.Message;
import wangmin.message.core.entity.MessageQuery;
import wangmin.message.core.entity.MessageStatus;
import wangmin.message.core.remote.MessageServiceInterface;
import wangmin.message.mgr_web.web.entity.CommonResponseBody;
import wangmin.message.mgr_web.web.entity.type.CommonResponseCode;

import java.util.List;

/**
 * <p>User: Wang Min
 * <p>Date: 2016-12-20
 * <p>Version: 1.0
 */
@RestController
@RequestMapping("/message")
@Api(description = "数据项的相关操作")
public class MessageController {

    @Autowired
    private MessageServiceInterface messageService;

	@RequestMapping(value = "/page", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "消息分页查询")
    public List<Message> page(
            @RequestParam("messageStatus") MessageStatus messageStatus,
            @RequestParam("orderBy") MessageQuery.Field field,
            @RequestParam("desc") boolean desc,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit) {
        MessageQuery messageQuery = new MessageQuery()
                .whereStatus(MessageQuery.CompareOperator.equal, messageStatus)
                .orderBy(field, desc)
                .limit(offset, limit);
        return messageService.queryMessageList(messageQuery);
    }

	/*@RequestMapping(value = "/setStatus/{messageId}", method = RequestMethod.PUT, produces = "application/json")
    @ApiOperation(value = "设置消息状态")
    public CommonResponseBody setStatus(
            @PathVariable("messageId") String messageId,
            @RequestParam("messageStatus") MessageStatus messageStatus) {
        messageService.reliveMessage(messageId);
        return new CommonResponseBody();
    }*/

    @RequestMapping(value = "/reliveAndSendMessage/{messageId}", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "复活发送消息状态")
    public CommonResponseBody reliveAndSendMessage(@PathVariable("messageId") String messageId) {
        if (messageService.reliveAndSendMessage(messageId))
            return new CommonResponseBody();
        else
            return new CommonResponseBody(CommonResponseCode.paramError, "messageId["+messageId+"], not exist");
    }
}
