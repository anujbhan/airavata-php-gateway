<!-- String replace is done as Jquery creates problems when using period(.) in id or class. -->
<div id="sr-{{ str_replace( '.', "_", $storageResource->storageResourceId) }}" class="@if(isset( $show) ) @if( !$show) hide @endif @else hide @endif">
<h3 class="text-center">Set Preferences</h3>
<div class="form-group">
    <label class="control-label col-md-3">Login Username</label>

    <div class="col-md-9">
        <input type="text" name="loginUserName" class="form-control"
               value="@if( isset( $preferences) ){{$preferences->loginUserName}}@endif"/>
    </div>
</div>
<!--
dmi preference might be needed in the future
<div class="form-group">
    <label class="control-label col-md-3">Preferred Data Movement Protocol</label>

    <div class="col-md-9">
        <select name="preferredDataMovementProtocol" class="form-control">
{{--            @foreach( (array)$computeResource->dataMovementInterfaces as $index => $dmi)--}}
            {{--<option value="{{ $dmi->dataMovementProtocol}}"--}}
{{--            @if( isset( $preferences) ) @if( $preferences->preferredDataMovementProtocol == $dmi->dataMovementProtocol)--}}
{{--            selected @endif @endif>{{ $crData["dataMovementProtocols"][$dmi->dataMovementProtocol] }}</option>--}}
            {{--@endforeach--}}
        </select>
    </div>
</div>
-->
<div class="form-group">
    <label class="control-label col-md-3">File System Root Location</label>

    <div class="col-md-9">
        <input type="text" name="fileSystemRootLocation" class="form-control"
               value="@if( isset( $preferences) ){{$preferences->fileSystemRootLocation}}@endif"/>
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-3">Resource Specific Credential Store Token</label>

    <div class="col-md-9">
        <select class="form-control gateway-credential-store-token" name="resourceSpecificCredentialStoreToken" >
            @if( isset( $preferences) )
            <option value="{{$preferences->resourceSpecificCredentialStoreToken}}">{{$preferences->resourceSpecificCredentialStoreToken}}</option>
            @else
            <option value="">Select a Credential Token from Store</option>
            @endif
            <option value="">DO-NO-SET</option>
            @foreach( $tokens as $token => $publicKey)
            <option value="{{$token}}">{{$token}}</option>
            @endforeach
        </select>
        <!--
        <input type="text" name="resourceSpecificCredentialStoreToken" class="form-control"
               value="@if( isset( $preferences) ){{$preferences->resourceSpecificCredentialStoreToken}}@endif"/>
        -->
    </div>
</div>

@if(Session::has("admin"))
<div class="form-group">
    <input type="submit" class="form-control btn btn-primary" value="Set preferences"/>
</div>
@endif
</div>