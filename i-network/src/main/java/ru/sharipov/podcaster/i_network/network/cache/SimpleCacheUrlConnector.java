/*
  Copyright (c) 2018-present, SurfStudio LLC.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package ru.sharipov.podcaster.i_network.network.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.HttpUrl;

/**
 * ищет {@link SimpleCacheInfo} для url запроса
 * Базовый класс для хранения информации об Url простого кеша.
 */
public class SimpleCacheUrlConnector {
    private static final String URL_SPLIT_REGEX = "[/?&]";
    private static final String BRACE = "{";

    @NonNull
    private final String baseUrl;

    @NonNull
    private final List<SimpleCacheInfo> simpleCacheInfo;

    public SimpleCacheUrlConnector(@NonNull String baseUrl, @NonNull Collection<SimpleCacheInfo> simpleCacheInfo) {
        this.baseUrl = baseUrl;
        this.simpleCacheInfo = new ArrayList<>(simpleCacheInfo);
        sortSimpleCacheInfo(this.simpleCacheInfo);
    }

    @SuppressWarnings("squid:S134")
    @Nullable
    public SimpleCacheInfo getByUrl(HttpUrl url, String method) {
        List<String> networkUrlSegments = getNetworkUrlSegments(url.toString());
        for (SimpleCacheInfo cacheInfo : simpleCacheInfo) {
            boolean baseCacheMethodCorresponds = checkApiMethod(method,
                    networkUrlSegments,
                    cacheInfo.getBaseApiMethod());

            if (baseCacheMethodCorresponds) {
                return cacheInfo;
            } else {
                for (SimpleCacheInfo.ApiMethod extraCacheMethod : cacheInfo.getExtraMethods()) {
                    boolean extraCacheMethodCorresponds = checkApiMethod(
                            method, networkUrlSegments, extraCacheMethod);
                    if (extraCacheMethodCorresponds) {
                        return cacheInfo;
                    }
                }
            }
        }
        return null;
    }

    @NonNull
    public Collection<SimpleCacheInfo> getSimpleCacheInfo() {
        return simpleCacheInfo;
    }

    @SuppressWarnings("squid:S135")
    private boolean checkApiMethod(String networkMethod,
                                   List<String> networkUrlSegments,
                                   SimpleCacheInfo.ApiMethod cacheApiMethod) {
        if (!networkMethod.equals(cacheApiMethod.getMethod())) {
            return false;
        }
        String[] cacheUrlSegments = cacheApiMethod.getUrl().split(URL_SPLIT_REGEX);
        if (cacheUrlSegments.length > networkUrlSegments.size()) {
            return false;
        }
        return checkSegmentsMatch(networkUrlSegments, cacheUrlSegments);
    }

    private boolean checkSegmentsMatch(List<String> networkUrlSegments, String[] cacheUrlSegments){
        return checkParametrizedSegmentsMatch(networkUrlSegments, cacheUrlSegments)
                && checkNonParametrizedSegmentsCountMatches(networkUrlSegments, cacheUrlSegments);
    }

    /**
     * Проверяем, что сегментам из cacheUrlSegments, которые содержат url параметр, например ?node=female
     * соответствуют сегменты в network networkUrlSegments, которые тоже содержат url параметр
     * и соответствующие сегменты равны между собой.
     *
     * @return true если соответствуют, иначе false
     */
    private boolean checkParametrizedSegmentsMatch(List<String> networkUrlSegments, String[] cacheUrlSegments) {
        for (int i = 0; i < cacheUrlSegments.length; i++) {
            String networkSegment = networkUrlSegments.get(i);
            String cacheSegment = cacheUrlSegments[i];
            if (isCacheUrlSegmentParameter(cacheSegment)) {
                continue;
            }
            boolean areSegmentsSame = networkSegment.equals(cacheSegment);
            if (!areSegmentsSame) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяем, что количество сегментов без параметров совпадает
     * <p>
     * Таким образом при сравнении cacheUrlSegments [brands]
     * и networkUrlSegments [brands, 503, has_top_products=0].
     * У networkUrlSegments будет отброшен последний элемент.
     * networkNonParametrizedCount в этом случае будет равен 2,
     * а cacheNonParametrizedCount равен 1 и метод вернет false.
     * <p>
     * При сравнении cacheUrlSegments [brands, {id}]
     * и networkUrlSegments [brands, 503, has_top_products=0]
     * метод вернет true, так как у networkUrlSegments будет отброшен
     * последий сегмент и количество сегментов совпадет.
     * <p>
     * При сравнении cacheUrlSegments [feed, node=female]
     * и networkUrlSegments [feed, node=female] в обоих случаях
     * будет отброшен последний элемент и метод вернет true,
     * так как количество совпадет.
     *
     * @return true если совпадает
     */
    private boolean checkNonParametrizedSegmentsCountMatches(List<String> networkUrlSegments, String[] cacheUrlSegments) {
        int networkNonParametrizedCount = getNonParametrizedSegmentsCount(networkUrlSegments);
        int cacheNonParametrizedCount = getNonParametrizedSegmentsCount(Arrays.asList(cacheUrlSegments));
        return networkNonParametrizedCount == cacheNonParametrizedCount;
    }

    private int getNonParametrizedSegmentsCount(List<String> segments) {
        int count = 0;
        for (String s : segments) {
            if (isNetworkUrlSegmentParameter(s)) {
                count++;
            }
        }
        return count;
    }

    @NonNull
    private List<String> getNetworkUrlSegments(String httpUrl) {
        String path = httpUrl.replaceAll(baseUrl, "");
        return Stream.of(path.split(URL_SPLIT_REGEX))
                .filterNot(TextUtils::isEmpty)
                .toList();
    }

    private static boolean isCacheUrlSegmentParameter(String cacheUrlPathSegment) {
        return cacheUrlPathSegment.startsWith(BRACE);
    }

    /**
     * Считаем часть сегментом, если она не содержит параметра
     */
    private boolean isNetworkUrlSegmentParameter(String cacheUrlPathSegment) {
        return !cacheUrlPathSegment.contains("=");
    }

    private static void sortSimpleCacheInfo(List<SimpleCacheInfo> simpleCacheInfo) {
        Collections.sort(simpleCacheInfo, (first, second) -> {
            String[] firstSegments = first.getBaseApiMethod().getUrl().split(URL_SPLIT_REGEX);
            String[] secondSegments = second.getBaseApiMethod().getUrl().split(URL_SPLIT_REGEX);
            int count = firstSegments.length > secondSegments.length ? secondSegments.length : firstSegments.length;
            for (int i = 0; i < count; i++) {
                if (isCacheUrlSegmentParameter(firstSegments[i]) && !isCacheUrlSegmentParameter(secondSegments[i])) {
                    return 1;
                }

                if (!isCacheUrlSegmentParameter(firstSegments[i]) && isCacheUrlSegmentParameter(secondSegments[i])) {
                    return -1;
                }
            }
            return 0;
        });
    }
}
