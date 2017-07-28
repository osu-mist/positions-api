package edu.oregonstate.mist.positions.core

import edu.oregonstate.mist.api.jsonapi.ResourceObject

class Position {
    String title
    String businessCenter
    String positionNumber
    String organizationCode

    ResourceObject toResourceObject() {
        new ResourceObject(
                type: "position",
                id: positionNumber,
                attributes: [
                        title: title,
                        businessCenter: businessCenter,
                        positionNumber: positionNumber,
                        organizationCode: organizationCode
                ]
        )
    }

}
