package com.society.server.service;

import com.society.server.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }
}
