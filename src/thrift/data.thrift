namespace java com.chm.thrift
namespace py py.thrift.generate

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean

struct People{
    1: optional string name,
    2: optional int age,
    3: optional boolean married
}

exception DataException {
    1: string message,
    2: string callStack,
    3: string date
}

service PeopleService {
    People getPeopleByName(1: required string name) throws (1: DataException dataException),
    void savePeople(1: required People people) throws (1: DataException dataException)
}