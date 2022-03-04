package com.xiaolong.netty.packet;

import com.xiaolong.netty.bean.Command;
import com.xiaolong.netty.bean.Packet;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestPacket extends Packet implements Command {

    private Integer userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

    public static LoginRequestPacket getLoginPacket (Integer userId, String username, String password){
        return LoginRequestPacket.builder().userId(userId).username(username).password(password).build();
    }
}
