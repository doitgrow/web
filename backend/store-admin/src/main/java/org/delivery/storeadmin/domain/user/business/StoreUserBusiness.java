package org.delivery.storeadmin.domain.user.business;


import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserService storeUserService;
    private final StoreUserConverter storeUserConverter;
    private final StoreRepository storeRepository;

    public StoreUserResponse register(
            StoreUserRegisterRequest request
    ) {
        var storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED);

        var storeUserEntity = storeUserConverter.toEntity(request, storeEntity.get());
        var newStoreUserEntity = storeUserService.register(storeUserEntity);

        var response = storeUserConverter.toResponse(
                newStoreUserEntity,
                storeEntity.get()
        );

        return response;
    }
}
