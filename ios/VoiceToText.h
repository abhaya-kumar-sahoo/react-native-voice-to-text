
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNVoiceToTextSpec.h"

@interface VoiceToText : NSObject <NativeVoiceToTextSpec>
#else
#import <React/RCTBridgeModule.h>

@interface VoiceToText : NSObject <RCTBridgeModule>
#endif

@end
