# DefaultApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**cancelCheckout**](DefaultApi.md#cancelCheckout) | **POST** /api/merchant/v1/checkouts/{checkoutUuid}/cancel | Cancel authorized checkout |
| [**captureCheckout**](DefaultApi.md#captureCheckout) | **POST** /api/merchant/v1/checkouts/{checkoutUuid}/capture | Capture authorized checkout |
| [**createCheckout**](DefaultApi.md#createCheckout) | **POST** /api/merchant/v1/checkouts | Create a new checkout |
| [**getCheckout**](DefaultApi.md#getCheckout) | **GET** /api/merchant/v1/checkouts/{checkoutUuid} | Get checkout details |
| [**getHealth**](DefaultApi.md#getHealth) | **GET** /api/health | Health check |
| [**getOAuthToken**](DefaultApi.md#getOAuthToken) | **POST** /oauth/token | Issue an access token |
| [**getSettlement**](DefaultApi.md#getSettlement) | **GET** /api/merchant/v1/settlements/{uuid} | Get a settlement by UUID |
| [**listSettlements**](DefaultApi.md#listSettlements) | **GET** /api/merchant/v1/settlements | List settlements for this merchant |
| [**refundCheckout**](DefaultApi.md#refundCheckout) | **POST** /api/merchant/v1/checkouts/{checkoutUuid}/refund | Refund a captured checkout |


<a id="cancelCheckout"></a>
# **cancelCheckout**
> CancelCheckoutResponse cancelCheckout(checkoutUuid, cancelCheckoutRequest)

Cancel authorized checkout

### Example
```java
// Import classes:
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.invoker.Configuration;
import co.maytes.api.invoker.auth.*;
import co.maytes.api.invoker.models.*;
import co.maytes.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");
    
    // Configure HTTP bearer authorization: bearer
    HttpBearerAuth bearer = (HttpBearerAuth) defaultClient.getAuthentication("bearer");
    bearer.setBearerToken("BEARER TOKEN");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String checkoutUuid = "checkoutUuid_example"; // String | 
    CancelCheckoutRequest cancelCheckoutRequest = new CancelCheckoutRequest(); // CancelCheckoutRequest | 
    try {
      CancelCheckoutResponse result = apiInstance.cancelCheckout(checkoutUuid, cancelCheckoutRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#cancelCheckout");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **checkoutUuid** | **String**|  | |
| **cancelCheckoutRequest** | [**CancelCheckoutRequest**](CancelCheckoutRequest.md)|  | |

### Return type

[**CancelCheckoutResponse**](CancelCheckoutResponse.md)

### Authorization

[bearer](../README.md#bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** |  |  -  |
| **401** |  |  -  |
| **404** |  |  -  |
| **409** |  |  -  |
| **422** |  |  -  |

<a id="captureCheckout"></a>
# **captureCheckout**
> CaptureCheckoutResponse captureCheckout(checkoutUuid, captureCheckoutRequest)

Capture authorized checkout

### Example
```java
// Import classes:
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.invoker.Configuration;
import co.maytes.api.invoker.auth.*;
import co.maytes.api.invoker.models.*;
import co.maytes.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");
    
    // Configure HTTP bearer authorization: bearer
    HttpBearerAuth bearer = (HttpBearerAuth) defaultClient.getAuthentication("bearer");
    bearer.setBearerToken("BEARER TOKEN");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String checkoutUuid = "checkoutUuid_example"; // String | 
    CaptureCheckoutRequest captureCheckoutRequest = new CaptureCheckoutRequest(); // CaptureCheckoutRequest | 
    try {
      CaptureCheckoutResponse result = apiInstance.captureCheckout(checkoutUuid, captureCheckoutRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#captureCheckout");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **checkoutUuid** | **String**|  | |
| **captureCheckoutRequest** | [**CaptureCheckoutRequest**](CaptureCheckoutRequest.md)|  | |

### Return type

[**CaptureCheckoutResponse**](CaptureCheckoutResponse.md)

### Authorization

[bearer](../README.md#bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** |  |  -  |
| **401** |  |  -  |
| **404** |  |  -  |
| **409** |  |  -  |
| **422** |  |  -  |

<a id="createCheckout"></a>
# **createCheckout**
> CreateCheckoutResponse createCheckout(createCheckoutRequest)

Create a new checkout

### Example
```java
// Import classes:
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.invoker.Configuration;
import co.maytes.api.invoker.auth.*;
import co.maytes.api.invoker.models.*;
import co.maytes.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");
    
    // Configure HTTP bearer authorization: bearer
    HttpBearerAuth bearer = (HttpBearerAuth) defaultClient.getAuthentication("bearer");
    bearer.setBearerToken("BEARER TOKEN");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    CreateCheckoutRequest createCheckoutRequest = new CreateCheckoutRequest(); // CreateCheckoutRequest | 
    try {
      CreateCheckoutResponse result = apiInstance.createCheckout(createCheckoutRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#createCheckout");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **createCheckoutRequest** | [**CreateCheckoutRequest**](CreateCheckoutRequest.md)|  | |

### Return type

[**CreateCheckoutResponse**](CreateCheckoutResponse.md)

### Authorization

[bearer](../README.md#bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** |  |  -  |
| **401** |  |  -  |
| **409** |  |  -  |
| **422** |  |  -  |

<a id="getCheckout"></a>
# **getCheckout**
> GetCheckoutResponse getCheckout(checkoutUuid, merchantOrderId)

Get checkout details

### Example
```java
// Import classes:
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.invoker.Configuration;
import co.maytes.api.invoker.auth.*;
import co.maytes.api.invoker.models.*;
import co.maytes.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");
    
    // Configure HTTP bearer authorization: bearer
    HttpBearerAuth bearer = (HttpBearerAuth) defaultClient.getAuthentication("bearer");
    bearer.setBearerToken("BEARER TOKEN");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String checkoutUuid = "checkoutUuid_example"; // String | 
    String merchantOrderId = "merchantOrderId_example"; // String | 
    try {
      GetCheckoutResponse result = apiInstance.getCheckout(checkoutUuid, merchantOrderId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getCheckout");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **checkoutUuid** | **String**|  | |
| **merchantOrderId** | **String**|  | [optional] |

### Return type

[**GetCheckoutResponse**](GetCheckoutResponse.md)

### Authorization

[bearer](../README.md#bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** |  |  -  |
| **401** |  |  -  |
| **404** |  |  -  |
| **422** |  |  -  |

<a id="getHealth"></a>
# **getHealth**
> HealthResponse getHealth()

Health check

### Example
```java
// Import classes:
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.invoker.Configuration;
import co.maytes.api.invoker.models.*;
import co.maytes.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      HealthResponse result = apiInstance.getHealth();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getHealth");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**HealthResponse**](HealthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** |  |  -  |

<a id="getOAuthToken"></a>
# **getOAuthToken**
> OAuthTokenResponse getOAuthToken(oauthTokenRequest)

Issue an access token

### Example
```java
// Import classes:
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.invoker.Configuration;
import co.maytes.api.invoker.models.*;
import co.maytes.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    OAuthTokenRequest oauthTokenRequest = new OAuthTokenRequest(); // OAuthTokenRequest | 
    try {
      OAuthTokenResponse result = apiInstance.getOAuthToken(oauthTokenRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getOAuthToken");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **oauthTokenRequest** | [**OAuthTokenRequest**](OAuthTokenRequest.md)|  | |

### Return type

[**OAuthTokenResponse**](OAuthTokenResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** |  |  -  |
| **400** | Invalid grant or request |  -  |
| **401** | Unauthorized |  -  |

<a id="getSettlement"></a>
# **getSettlement**
> GetSettlementResponse getSettlement(uuid)

Get a settlement by UUID

### Example
```java
// Import classes:
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.invoker.Configuration;
import co.maytes.api.invoker.auth.*;
import co.maytes.api.invoker.models.*;
import co.maytes.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");
    
    // Configure HTTP bearer authorization: bearer
    HttpBearerAuth bearer = (HttpBearerAuth) defaultClient.getAuthentication("bearer");
    bearer.setBearerToken("BEARER TOKEN");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String uuid = "uuid_example"; // String | Settlement UUID
    try {
      GetSettlementResponse result = apiInstance.getSettlement(uuid);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getSettlement");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **uuid** | **String**| Settlement UUID | |

### Return type

[**GetSettlementResponse**](GetSettlementResponse.md)

### Authorization

[bearer](../README.md#bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** |  |  -  |
| **401** |  |  -  |
| **404** |  |  -  |

<a id="listSettlements"></a>
# **listSettlements**
> ListSettlementsResponse listSettlements(offset, limit, sortDir, sortBy, createdBefore, createdAfter, status)

List settlements for this merchant

Returns settlements for the authenticated merchant. Default sort: created_at ASC.

### Example
```java
// Import classes:
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.invoker.Configuration;
import co.maytes.api.invoker.auth.*;
import co.maytes.api.invoker.models.*;
import co.maytes.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");
    
    // Configure HTTP bearer authorization: bearer
    HttpBearerAuth bearer = (HttpBearerAuth) defaultClient.getAuthentication("bearer");
    bearer.setBearerToken("BEARER TOKEN");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Object offset = null; // Object | Pagination offset (default 0)
    Object limit = null; // Object | Max records to return (default 50, max 200)
    String sortDir = "asc"; // String | Sort direction (default: asc)
    String sortBy = "created_at"; // String | Field to sort by (default: created_at)
    Object createdBefore = null; // Object | Include settlements created at or before this ISO-8601 timestamp
    Object createdAfter = null; // Object | Include settlements created at or after this ISO-8601 timestamp
    String status = "CREATED"; // String | Filter by status
    try {
      ListSettlementsResponse result = apiInstance.listSettlements(offset, limit, sortDir, sortBy, createdBefore, createdAfter, status);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#listSettlements");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **offset** | [**Object**](.md)| Pagination offset (default 0) | [optional] |
| **limit** | [**Object**](.md)| Max records to return (default 50, max 200) | [optional] |
| **sortDir** | **String**| Sort direction (default: asc) | [optional] [enum: asc, desc] |
| **sortBy** | **String**| Field to sort by (default: created_at) | [optional] [enum: created_at, period_to, settled_amount] |
| **createdBefore** | [**Object**](.md)| Include settlements created at or before this ISO-8601 timestamp | [optional] |
| **createdAfter** | [**Object**](.md)| Include settlements created at or after this ISO-8601 timestamp | [optional] |
| **status** | **String**| Filter by status | [optional] [enum: CREATED, PENDING, COMPLETED, FAILED, NEEDS_REVIEW, CANCELLED] |

### Return type

[**ListSettlementsResponse**](ListSettlementsResponse.md)

### Authorization

[bearer](../README.md#bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** |  |  -  |
| **401** |  |  -  |

<a id="refundCheckout"></a>
# **refundCheckout**
> refundCheckout(checkoutUuid, createRefundRequest)

Refund a captured checkout

Initiates a full refund of the captured payment. Returns 204 immediately; Stripe confirms asynchronously via webhook. Poll GET /checkouts/:uuid for status and refund details.

### Example
```java
// Import classes:
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.invoker.Configuration;
import co.maytes.api.invoker.auth.*;
import co.maytes.api.invoker.models.*;
import co.maytes.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");
    
    // Configure HTTP bearer authorization: bearer
    HttpBearerAuth bearer = (HttpBearerAuth) defaultClient.getAuthentication("bearer");
    bearer.setBearerToken("BEARER TOKEN");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String checkoutUuid = "checkoutUuid_example"; // String | 
    CreateRefundRequest createRefundRequest = new CreateRefundRequest(); // CreateRefundRequest | 
    try {
      apiInstance.refundCheckout(checkoutUuid, createRefundRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#refundCheckout");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **checkoutUuid** | **String**|  | |
| **createRefundRequest** | [**CreateRefundRequest**](CreateRefundRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

[bearer](../README.md#bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** |  |  -  |
| **401** |  |  -  |
| **402** |  |  -  |
| **404** |  |  -  |
| **409** |  |  -  |

