package com.aws.demo.data.model

enum class AwsPlaceType {
    AddressType,
    StreetType,
    PointOfInterestType;

    companion object {
        fun getAllPlaceTypes(): List<AwsPlaceType> {
            return entries
        }
    }
}