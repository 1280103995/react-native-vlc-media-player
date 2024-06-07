if(NOT TARGET hermes-engine::libhermes)
add_library(hermes-engine::libhermes SHARED IMPORTED)
set_target_properties(hermes-engine::libhermes PROPERTIES
    IMPORTED_LOCATION "/Users/fjliang/.gradle/caches/transforms-4/b2a09668d3c8974d34f616c8ac6413fe/transformed/jetified-hermes-android-0.74.1-debug/prefab/modules/libhermes/libs/android.x86/libhermes.so"
    INTERFACE_INCLUDE_DIRECTORIES "/Users/fjliang/.gradle/caches/transforms-4/b2a09668d3c8974d34f616c8ac6413fe/transformed/jetified-hermes-android-0.74.1-debug/prefab/modules/libhermes/include"
    INTERFACE_LINK_LIBRARIES ""
)
endif()

