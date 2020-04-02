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
package ru.sharipov.podcaster.i_network.network.error;

/**
 * коды ошибок
 */
public class HttpCodes {

    public static final int CODE_200 = 200; //успех
    public static final int CODE_400 = 400;
    public static final int CODE_401 = 401; //невалидный токен
    public static final int CODE_429 = 429; //Закончился лимит бесплатных запросов
    public static final int CODE_404 = 404;
    public static final int CODE_500 = 500; //ошибка сервера
}
