package BLL.Services.Requests;

import java.util.List;

public record FriendAccountDTO(String friendLogin, List<AccountDTO> friendAccounts) {
}
